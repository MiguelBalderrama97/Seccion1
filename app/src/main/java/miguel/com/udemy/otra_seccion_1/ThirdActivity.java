package miguel.com.udemy.otra_seccion_1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText etxt;
    private ImageButton ibtn;

    private final int PHONE_CALL_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        etxt = findViewById(R.id.editText);
        ibtn = findViewById(R.id.imageButton);

        ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNumber = etxt.getText().toString();

                if (sNumber.isEmpty()) {
                    Toast.makeText(ThirdActivity.this, "Missing number", Toast.LENGTH_SHORT).show();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                    } else {
                        olderVersions(sNumber);
                    }
                }
            }

            private void olderVersions(String phoneNumber) {
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                if (ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intentCall);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PHONE_CALL_CODE:

                String sResu = permissions[0];
                int iResu = grantResults[0];

                if (sResu.equals(Manifest.permission.CALL_PHONE))
                    if (iResu == PackageManager.PERMISSION_GRANTED) {
                        String sNumber = etxt.getText().toString();
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("Phone: " + sNumber));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
                    }else{
                        Toast.makeText(ThirdActivity.this, "You declined the access", Toast.LENGTH_SHORT).show();

                    }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
}
