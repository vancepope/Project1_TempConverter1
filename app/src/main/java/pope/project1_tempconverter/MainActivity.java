package pope.project1_tempconverter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.View.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements OnEditorActionListener, OnClickListener {



@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private EditText fahrenEt;
    private TextView celsiusNumTv;
    private String fahrenheitDegree = "";
    private Button calculateBtn;
    private float celsiusDegree = 0.0f;
    private SharedPreferences saved;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get reference to the widget
        fahrenEt = (EditText) findViewById(R.id.fahrenEt);
        celsiusNumTv = (TextView) findViewById(R.id.celsiusNumTv);
        calculateBtn = (Button) findViewById(R.id.calculateBtn);

        // Set the listener

        celsiusNumTv.setOnEditorActionListener(this);
        calculateBtn.setOnClickListener(this);

        saved = getSharedPreferences("saved",MODE_PRIVATE);

    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.calculateBtn:
                calculateAndDisplay();
                break;
        }
    }
    public void calculateAndDisplay(){
        // Get the degree amount
        fahrenheitDegree = fahrenEt.getText().toString();
        float fDegreeAmt;
        if (fahrenheitDegree.equals("")){
            fDegreeAmt = 0;
        }else {
            fDegreeAmt = Float.parseFloat(fahrenheitDegree);
        }

        //Convert Fahrenheit temp to Celsius temp
        celsiusDegree = (fDegreeAmt - 32) * 0.5556f;
        NumberFormat celsius = NumberFormat.getNumberInstance();

        //Set celsius num
        celsiusNumTv.setText(celsius.format(celsiusDegree));
    }
    public void onPause(){
        Editor edit = saved.edit();
        edit.putString("fahrenheitDegree", fahrenheitDegree);
        edit.commit();

        super.onPause();
    }
    public void onResume(){
        super.onResume();

        fahrenheitDegree = saved.getString("fahrenheitDegree", "");
        celsiusNumTv.setText(fahrenheitDegree);
            //calculateAndDisplay();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

