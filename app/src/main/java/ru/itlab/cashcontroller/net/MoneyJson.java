package ru.itlab.cashcontroller.net;

public class MoneyJson {
    public String disclaimer;
    public String date;
    public String timestamp;
    public String base;
    public Rates rates;

    @Override
    public String toString() {
        return "MoneyJson{" +
                "disclaimer='" + disclaimer + '\'' +
                ", date='" + date + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", base='" + base + '\'' +
                ", rates=" + rates +
                '}';
    }

    public class Rates {
//        public float AUD;
//        public float AZN;
//        public float GBP;
//        public float AMD;
//        public float BYN;
//        public float BGN;
//        public float BRL;
//        public float HUF;
//        public float HKD;
//        public float DKK;
        public float USD;
        public float EUR;
//        public float INR;
//        public float KZT;
//        public float CAD;
//        public float KGS;
//        public float CNY;
//        public float MDL;
//        public float NOK;
//        public float PLN;
//        public float RON;
//        public float XDR;
//        public float SGD;
//        public float TJS;
//        public float TRY;
//        public float TMT;
//        public float UZS;
//        public float UAH;
//        public float CZK;
//        public float SEK;
//        public float CHF;
//        public float ZAR;
//        public float KRW;
//        public float JPY;

        @Override
        public String toString() {
            return "Rates{" +
//                    "AUD=" + AUD +
//                    ", AZN=" + AZN +
//                    ", GBP=" + GBP +
//                    ", AMD=" + AMD +
//                    ", BYN=" + BYN +
//                    ", BGN=" + BGN +
//                    ", BRL=" + BRL +
//                    ", HUF=" + HUF +
//                    ", HKD=" + HKD +
//                    ", DKK=" + DKK +
                    ", USD=" + USD +
                    ", EUR=" + EUR +
//                    ", INR=" + INR +
//                    ", KZT=" + KZT +
//                    ", CAD=" + CAD +
//                    ", KGS=" + KGS +
//                    ", CNY=" + CNY +
//                    ", MDL=" + MDL +
//                    ", NOK=" + NOK +
//                    ", PLN=" + PLN +
//                    ", RON=" + RON +
//                    ", XDR=" + XDR +
//                    ", SGD=" + SGD +
//                    ", TJS=" + TJS +
//                    ", TRY=" + TRY +
//                    ", TMT=" + TMT +
//                    ", UZS=" + UZS +
//                    ", UAH=" + UAH +
//                    ", CZK=" + CZK +
//                    ", SEK=" + SEK +
//                    ", CHF=" + CHF +
//                    ", ZAR=" + ZAR +
//                    ", KRW=" + KRW +
//                    ", JPY=" + JPY +
                    '}';
        }
    }
}
