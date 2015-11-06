package cn.wydewy.filedinhand.util;

/**
 * Created by freddy on 15/7/25.
 */
public class DistanceCaculator {



    public static double eps=(Math.pow(1.0, -12));
    public static double pi = 3.14159265359;
    public static double a=6378137;
    public static double b=6356752.3142;
    public double average=6371012;
    public double r1=0;
    public double r2=0;
    public double theta1=0;
    public double theta2=0;


    public double f(double x){
        double result=0;
        double tmp=0;

        tmp= Math.pow(a,2)*Math.cos(x)*Math.cos(x)+Math.pow(b,2)*Math.sin(x)*Math.sin(x);
        result=Math.sqrt(tmp);


        return result;
    }


    public double simpson(double a,double b){
        double result=0;
        double c = a + (b-a)/2;
        result=(f(a)+4*f(c)+f(b))*(b-a)/6;
        return result;
    }


    public double getr(double t){
        double result=0;
        double tmp=0;
        tmp=(Math.sin(t)*Math.sin(t))/(Math.cos(t)*Math.cos(t));
        result=Math.sqrt((1+tmp)/(a*a+tmp/(b*b)));
        return result;
    }


    public double solve(double l,double r){
        double result=0;
        result= asr(l, r, eps, simpson(l, r));
        return result;
    }


    public double asr(double a,double b,double epss,double A){
        double result=0;

        double c = a+(b-a)/2;
        double L = simpson(a,c) , R = simpson(c,b);
        if (Math.abs(L+R-A) <= 15*epss) return L+R+(L+R-A)/15;
        return asr(a,c,epss/2,L) + asr(c,b,epss/2,R);
    }



    public void setter(double th1,double th2){
        theta1=th1;
        theta2=th2;
    }

    public double getter(){
        double r1;
        double r2;
        double result=0;
        r1=getr(theta1);
        r2=getr(theta2);
        result =Math.abs(solve(Math.asin(r1*Math.cos(theta1)/a),Math.asin(r2*Math.cos(theta2)/a)));
        return result;
    }

}
