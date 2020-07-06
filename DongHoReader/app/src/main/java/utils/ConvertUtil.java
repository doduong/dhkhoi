package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Admins on 12/29/2016.
 */

public class ConvertUtil {
    private static String splat_xuoi="delimiter_splat_xuoi";
    private static String dau_thang="delimiter_thang";
    private static String hoi_cham="delimiter_hoi_cham";
    private static String splat_nguoc="delimiter_splat_nguoc";
    private static String dau_sao="delimiter_dau_sao";
    private static String dau_phan_tram="delimiter_dau_phantram";

    public static String Repalce_char_special(String str)
    {
        try {
            str = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        str = str.replaceAll("\\+", "%20");
        str = str.replaceAll("%2F", splat_xuoi);
        str = str.replaceAll("%23", dau_thang);
        str = str.replaceAll("%3F", hoi_cham);
        str = str.replaceAll("%5C", splat_nguoc);
        str = str.replaceAll("%25", dau_phan_tram);
        return str;
    }
    public static String formatNumberDouble(double number) {

        try {
            DecimalFormat df = new DecimalFormat("####0.00");
            String temp=df.format(number);
            String[] parts= temp.split("\\,") ;
            if(parts[1].equals("00"))
                parts[1]="0";
            if (parts.length>1)
            {
                int step=parts[1].length()>=2?2:0;
                String ext = parts[1].substring(0, step);
                NumberFormat formatter = new DecimalFormat("###,###");
                String resp = formatter.format(Double.parseDouble(parts[0]));
                resp = resp.replaceAll(",", ".");
                if(step>0)
                    return  resp + "," + ext;
                return resp;
            }
            else
            {
                NumberFormat formatter = new DecimalFormat("###,###");
                String resp = formatter.format(number);
                resp = resp.replaceAll(",", ".");
                return resp ;
            }
        } catch (Exception e) {
            return "";
        }
    }
    public static String formatNumberVN(double number) {

        try {
            NumberFormat formatter = new DecimalFormat("###,###");
            String resp = formatter.format(number);
            resp = resp.replaceAll(",", ".");
            return resp;
        } catch (Exception e) {
            return "";
        }
    }
    public static String hexToDecimal (String s)

    {

        if (s == null || s.length() == 0)

            throw new IllegalArgumentException("null string argument");



        int decValue = 0;

        int from = 0;

        if(s.charAt(0) == '+')

            from = 1;

        for (int i = from; i < s.length(); i++)

        {

            decValue *= 16;              // not ++;

            char hexchar = s.charAt(i);

            if (hexchar >= 'A' && hexchar <= 'F')      // && not ||

                decValue += 10 + (hexchar - 'A');

            else if (hexchar >= '0' && hexchar <= '9')

                decValue += hexchar - '0';

            else

                throw new IllegalArgumentException("Invalid character: " + hexchar);

        }

        return "" + decValue;

    }
    public static String InsertBlank(String data, int length, int align)
    {
        String result ="";
        int lendata= data.length();
        if(lendata<length)
        {
            if(align==1)
            {
                result=data+getSpaces(length-lendata);
            }else if(align==2)
            {
                result=getSpaces(length-lendata)+data;
            }else
            {
                int left=(int)(length-lendata)/2;
                result=getSpaces(left)+data + getSpaces(length-lendata-left);
            }
        }
        return  result;
    }
    public static String getSpaces(int len)
    {
        String result="";
        for(int i=0;i<len;i++)
        {
            result+=" ";
        }
        return result;
    }
    public static String getSpaces(int len, String _char)
    {
        String result="";
        for(int i=0;i<len;i++)
        {
            result+=_char;
        }
        return result;
    }
    public static String ConvertToStringAndNewLine(String input)
    {
        if (input.length()<28)
            return  input;
        String result ="";
        String part []= input.split(" ");
        int i=0;
        String partpara []=null;
        String temp="";
        for (String item :part )
        {
            if(i>0)
            {
                partpara=result.split("\n");
                if(partpara.length==i+1)
                    temp=partpara[i];
                else
                    temp="";
            }

            else
                 temp=result.replace("\n","");


             if( (temp+" "+ item).length()<=28)
             {
                 if(result.length()==0)
                     result=item;
                 else
                     result+=" "+item;

             }
             else
             {
                 result+="\n"+item;
                 i++;
             }


        }
        return  result;
    }
}
