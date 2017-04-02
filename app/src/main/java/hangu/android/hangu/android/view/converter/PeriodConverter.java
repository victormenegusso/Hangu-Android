package hangu.android.hangu.android.view.converter;

/**
 * Created by Victor Menegusso on 02/04/17.
 */

public class PeriodConverter {

    public static long getValueFromIndex(int index){
        switch (index){
            case 0 : return 0;
            case 1 : return 10000;
            case 2 : return 30000;
            case 3 : return 60000;
            case 4 : return 300000;
            case 5 : return 1800000;
        }
        return 0;
    }

    public static String getLabelFromValue(long value){
        if(value == 10000)
            return  "10 s";
        else if(value == 30000)
            return  "30 s";
        else if(value == 60000)
            return  "1 min";
        else if(value == 300000)
            return  "5 min";
        else if(value == 1800000)
            return  "30 min";

        return "0 (não monitorado)";
    }

    public static int getIndexFromValue(long value){
        switch ((int)value){
            case 0 : return 0;
            case 10000 : return 1;
            case 30000 : return 2;
            case 60000 : return 3;
            case 300000 : return 4;
            case 1800000 : return 5;
        }
        return 0;
    }

    public static String[] getArrayValues(){
        String[] spinnerArray = new String[6];

        spinnerArray[0] = "0 (não monitorado)";
        spinnerArray[1] = "10 s";
        spinnerArray[2] = "30 s";
        spinnerArray[3] = "1 min";
        spinnerArray[4] = "5 min";
        spinnerArray[5] = "30 min";

        return spinnerArray;
    }
}
