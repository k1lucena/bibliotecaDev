package k1.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil {
    public static Date StringToDate(String date) throws ParseException{
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date dataFormatada = format.parse(date);
            return dataFormatada;
        }
}
