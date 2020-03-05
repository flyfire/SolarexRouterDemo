package com.solarexsoft.solarexrouterdemo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.solarexsoft.solarexrouter.annotation.SolarexExtra;
import com.solarexsoft.solarexrouter.annotation.SolarexRouter;
import com.solarexsoft.solarexroutercore.SolarexRouterCore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 09:18/2020/3/5
 *    Desc:
 * </pre>
 */
@SolarexRouter(path = "/main/demoextra")
public class DemoExtraActivity extends AppCompatActivity {
    private static final String TAG = "DemoExtraActivity";

    @SolarexExtra
    String a;
    @SolarexExtra
    int b;
    @SolarexExtra
    short c;
    @SolarexExtra
    long d;
    @SolarexExtra
    float e;
    @SolarexExtra
    double f;
    @SolarexExtra
    byte g;
    @SolarexExtra
    boolean h;
    @SolarexExtra
    char i;


    @SolarexExtra
    String[] aa;
    @SolarexExtra
    int[] bb;
    @SolarexExtra
    short[] cc;
    @SolarexExtra
    long[] dd;
    @SolarexExtra
    float[] ee;
    @SolarexExtra
    double[] ff;
    @SolarexExtra
    byte[] gg;
    @SolarexExtra
    boolean[] hh;
    @SolarexExtra
    char[] ii;

    @SolarexExtra
    DemoParcelable j;
    @SolarexExtra
    DemoParcelable[] jj;


    @SolarexExtra
    List<DemoParcelable> k1;
    @SolarexExtra
    ArrayList<DemoParcelable> k2;

    @SolarexExtra
    List<String> k3;

    @SolarexExtra
    List<Integer> k4;

    @SolarexExtra(name = "hhhhhh")
    int test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demoextra);
        SolarexRouterCore.getInstance().injectExtras(this);
        TextView textView = findViewById(R.id.tv);
        textView.setText(toString());
    }

    @NonNull
    @Override
    public String toString() {
        return "DemoExtraActivity{" +
                "a='" + a + '\'' +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                ", e=" + e +
                ", f=" + f +
                ", g=" + g +
                ", h=" + h +
                ", i=" + i +
                ", aa=" + Arrays.toString(aa) +
                ", bb=" + Arrays.toString(bb) +
                ", cc=" + Arrays.toString(cc) +
                ", dd=" + Arrays.toString(dd) +
                ", ee=" + Arrays.toString(ee) +
                ", ff=" + Arrays.toString(ff) +
                ", gg=" + Arrays.toString(gg) +
                ", hh=" + Arrays.toString(hh) +
                ", ii=" + Arrays.toString(ii) +
                ", j=" + j +
                ", jj=" + Arrays.toString(jj) +
                ", k1=" + k1 +
                ", k2=" + k2 +
                ", k3=" + k3 +
                ", k4=" + k4 +
                '}';
    }
}
