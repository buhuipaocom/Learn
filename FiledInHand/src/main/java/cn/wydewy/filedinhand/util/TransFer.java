package cn.wydewy.filedinhand.util;

public class TransFer {

    double PI;
    double a;
    double b;
    double c;
    double e;
    double X;
    double Y;
    double Z;
    double W;
    double N;


    public TransFer(double B,double L, double H){

            PI=3.1415926535897932384626433832795;
            a= 6378137.000;
            b= 6356752.314;
            c =( a*a )/ b;
            e = Math.sqrt((a*a - b*b)) / a;

            W = Math.sqrt(1 - e * e * Math.sin(B) * Math.sin(B));
            N = a / W;
            X = (N + H)*Math.cos(B)*Math.cos(L);
            Y = (N + H)*Math.cos(B)*Math.sin(L);
            Z = (N*(1 - e*e) + H)*Math.sin(B);
        }

    public double getX(){
        return X;
    }
    public double getY(){
        return Y;
    }
    public double getZ(){
        return Z;
    }
}
