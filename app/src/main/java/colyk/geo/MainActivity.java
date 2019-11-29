package colyk.geo;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.multidots.fingerprintauth.AuthErrorCodes;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

public class MainActivity extends AppCompatActivity implements FingerPrintAuthCallback {

    private FingerPrintAuthHelper mFingerPrintAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);
        mFingerPrintAuthHelper.startAuth();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingerprint);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //start finger print authentication
        mFingerPrintAuthHelper.startAuth();
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        Toast.makeText(this, "Your device doesn't support fingerprint authentication", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoFingerPrintRegistered() {
    }

    @Override
    public void onBelowMarshmallow() {
        Toast.makeText(this, "Your device doesn't support fingerprint authentication", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        //Authentication sucessful.
        Toast.makeText(this, "Cool", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        switch (errorCode) {    //Parse the error code for recoverable/non recoverable error.
            case AuthErrorCodes.CANNOT_RECOGNIZE_ERROR:
                Toast.makeText(this, "Cannot recognize the fingerprint", Toast.LENGTH_SHORT).show();
                break;
            case AuthErrorCodes.NON_RECOVERABLE_ERROR:
                //This is not recoverable error. Try other options for user authentication. like pin, password.
                break;
            case AuthErrorCodes.RECOVERABLE_ERROR:
                Toast.makeText(this, "Cannot recognize the fingerprint", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}