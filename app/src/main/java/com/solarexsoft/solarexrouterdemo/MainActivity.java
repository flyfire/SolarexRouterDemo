package com.solarexsoft.solarexrouterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.solarexsoft.solarexrouter.annotation.SolarexRouter;
import com.solarexsoft.solarexroutercore.SolarexRouterCore;

import java.util.ArrayList;

@SolarexRouter(path = "/main/mainpage")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SolarexRouterCore.getInstance().init(getApplication());
        findViewById(R.id.btn_demoextra).setOnClickListener(this);
        findViewById(R.id.btn_modulea).setOnClickListener(this);
        findViewById(R.id.btn_moduleb).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_demoextra) {
            jumpDemoExtra();
        } else if (id == R.id.btn_modulea) {

        } else if (id == R.id.btn_moduleb) {

        }
    }

    private void jumpDemoExtra() {
        ArrayList<Integer> integers = new ArrayList<Integer>();
        integers.add(1);
        integers.add(2);

        ArrayList<String> strings = new ArrayList<String>();
        strings.add("1");
        strings.add("2");

        ArrayList<DemoParcelable> ps = new ArrayList<DemoParcelable>();

        DemoParcelable testParcelable = new DemoParcelable(1, "a");
        DemoParcelable testParcelable2 = new DemoParcelable(2, "d");
        ps.add(testParcelable);
        ps.add(testParcelable2);

        SolarexRouterCore.getInstance()
                .build("/main/demoextra")
                .withString("a", "FromMainActivity")
                .withInt("b", 1)
                .withShort("c", (short) 2)
                .withLong("d", 3)
                .withFloat("e", 1.0f)
                .withDouble("f", 1.1)
                .withByte("g", (byte) 1)
                .withBoolean("h", true)
                .withChar("i", '中')
                .withParcelable("j", testParcelable)
                .withStringArray("aa",new String[]{"1", "2"})
                .withIntArray("bb", new int[]{1, 2})
                .withShortArray("cc", new short[]{(short) 2, (short) 2})
                .withLongArray("dd", new long[]{1, 2})
                .withFloatArray("ee", new float[]{1.0f, 1.0f})
                .withDoubleArray("ff", new double[]{1.1, 1.1})
                .withByteArray("gg", new byte[]{(byte) 1, (byte) 1})
                .withBooleanArray("hh", new boolean[]{true, true})
                .withCharArray("ii", new char[]{'中', '中'})
                .withParcelableArray("jj", new DemoParcelable[]{testParcelable, testParcelable2})
                .withParcelableArrayList("k1", ps).withParcelableArrayList("k2", ps)
                .withStringArrayList("k3", strings).withIntegerArrayList("k4", integers)
                .withInt("hhhhhh", 1)
                .navigation(this, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "requestCode: " + requestCode + ",resultCode: " + resultCode);
    }
}
