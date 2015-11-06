package cn.wydewy.filedinhand;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends Activity implements OnClickListener {
	private EditText usernameET;
	private EditText passwordET;

	private TextView signUpTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		initView();
		initEvevt();
	}

	private void initEvevt() {
		// TODO Auto-generated method stub
		signUpTV.setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		usernameET = (EditText) findViewById(R.id.usernameET);
		passwordET = (EditText) findViewById(R.id.passwordET);
		signUpTV = (TextView) findViewById(R.id.signUpTV);

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.signUpTV:
			signUp();
			break;
		}
	}

	/**
	 * 
	 */
	private void signUp() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "signUp", Toast.LENGTH_SHORT).show();
	}
}
