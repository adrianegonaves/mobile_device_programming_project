package pdm.projeto.vivabemapp;

import android.util.Log;

import java.util.Calendar; // for the math between dates

public class AmDate {
    public final static String STAMP = "@AmDate";
    int mY, mM, mD;
    Calendar mC; // synchronized with mY, mM, mD

    @Override
    public String toString(){
        String ymd =
                String.format(
                        "%04d-%02d-%02d",
                        this.mY,
                        this.mM,
                        this.mD
                );
        return ymd;
    }//toString

    public static boolean validYear(int pY){
        return pY>=1;
    }//validYear

    public static boolean validMonth(int pM){
        return pM>=1 && pM<=12;
    }//validMonth

    public static boolean validDay(int pY, int pM, int pD){
        return pD>=1 && pD<=AmDate.numberOfDaysInMonth(pY, pM);
    }//validDay

    public AmDate(String pYMD) {
        if (pYMD.trim().isEmpty()){
            throw new IllegalArgumentException
                    ("Date from empty string");
        }
        try{
            String[] parts =
                    pYMD.split("-");
            int y = Integer.valueOf(parts[0]);
            int m = Integer.valueOf(parts[1]);
            int d = Integer.valueOf(parts[2]);
            boolean bValidDate =
                    AmDate.validYear(y) &&
                            AmDate.validMonth(m) &&
                            AmDate.validDay(y, m, d);

            if (bValidDate){ // option: force the Exception when the date is not valid
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, y);
                c.set(Calendar.MONTH, m-1);
                c.set(Calendar.DAY_OF_MONTH, d);

                this.mC=c;
                this.mY=y;
                this.mM=m;
                this.mD=d;
            }
            else{
                throw new IllegalArgumentException(
                        pYMD+" NOT a valid date"
                );
            }
        }
        catch (Exception e){
            // will happen for date "a-b-c", for example
            Log.e(
                    STAMP,
                    "Invalid date from string "+pYMD
            );
            throw new IllegalArgumentException(
                    "Invalid date: " + pYMD,
                    e
            );
        }//catch
    }//AmDate

    public AmDate(int pY, int pM, int pD){
        this.mY = pY; // 2026
        this.mM = pM; // 11
        this.mD = pD; // 2

        // synchronize Calendar with the other data members
        this.mC = Calendar.getInstance();
        this.mC.set(Calendar.YEAR, mY);

        // month in Calendar is 0-based
        // so, to keep the synch with the natural
        // representation, subtract 1
        this.mC.set(Calendar.MONTH, mM-1);

        this.mC.set(Calendar.DAY_OF_MONTH, mD);

        // TODO: check if the date is possible
    }//AmDate

    long distanceInDays(Calendar pC){
        long otherMS = pC.getTimeInMillis();
        long selfMS = this.mC.getTimeInMillis();
        long dif = Math.abs(
                otherMS - selfMS
        );
        return dif/1000/60/60/24;
    }//distanceInDays

    public static boolean leapYear(int pY){
        boolean c1, c2;
        c1 = pY%4==0 && pY%100!=0;
        c2 = pY%400==0;
        return c1 || c2;
    }//leapYear

    public static int numberOfDaysInMonth(
            int pY,
            int pM
    ){
        boolean c31 =
                pM==1 || pM==3 || pM==5 || pM==7 ||
                        pM==8 || pM==10 || pM==12;
        boolean c30 =
                pM==4 || pM==6 || pM==9 || pM==11;

        if (c31) return 31;
        if (c30) return 30;
        if (pM==2) return leapYear(pY) ? 29: 28;

        return -1;
    }//numberOfDaysInMonth
}//AmDate
