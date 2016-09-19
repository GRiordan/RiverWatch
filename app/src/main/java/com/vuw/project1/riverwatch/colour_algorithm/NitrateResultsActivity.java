package com.vuw.project1.riverwatch.colour_algorithm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.database.Database;
import com.vuw.project1.riverwatch.ui.MainActivity;
import com.vuw.project1.riverwatch.ui.NitrateActivity;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

public class NitrateResultsActivity extends AppCompatActivity {
    private Button finishButton;
    private Button doneButton;
    private Button abortButton;

    private static final String TAG = CameraActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitrate_results);

        TextView nitrateView = (TextView) findViewById(R.id.nitrateTextView);
        TextView nitriteView = (TextView) findViewById(R.id.nitriteTextView);
        ImageView leftView = (ImageView) findViewById(R.id.nitrateImageView);
        ImageView rightView = (ImageView) findViewById(R.id.nitriteImageView);

        Intent intent = getIntent();
        final Double nitrate = intent.getExtras().getDouble("nitrate");
        final Double nitrite = intent.getExtras().getDouble("nitrite");

        // set image views to left and right square of strip
        Bitmap left = (Bitmap) intent.getParcelableExtra("left");
        Bitmap right = (Bitmap) intent.getParcelableExtra("right");
        leftView.setImageBitmap(left);
        rightView.setImageBitmap(right);

        // set text views to nitrite and nitrate levels
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        nitrateView.setText("Nitrate: " + df.format(nitrate));
        nitriteView.setText("Nitrite: " + df.format(nitrite));

        finishButton = (Button) findViewById(R.id.finishButton);
        doneButton = (Button) findViewById(R.id.doneButton);
        abortButton = (Button) findViewById(R.id.abortButton);


        // button for closing the virtual keyboard
        doneButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                }
        );

        // button for aborting the report
        abortButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NitrateResultsActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

        // button for finishing the report and adding it to the history
        finishButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = (EditText) findViewById(R.id.infoEditText);
                        String info = editText.getText().toString();
                        NitrateResult result = new NitrateResult(nitrate, nitrite, info);

                        //TODO: add code to add this result to history

                        DialogFragment saveDialog = new SaveToDatabaseDialogFragment();
                        Database db = new Database(NitrateResultsActivity.this);
                        Bundle bundle = new Bundle();
                        bundle.putString("location", "temp");
                        bundle.putString("description", info);
                        bundle.putString("date", new Date(System.currentTimeMillis()).toString());
                        bundle.putString("image", "temp image");
                        bundle.putDouble("nitrate", nitrate);
                        bundle.putDouble("nitrite", nitrite);
                        bundle.putDouble("longitude", 10);
                        bundle.putDouble("latitude", 10);
                        saveDialog.setArguments(bundle);
                        saveDialog.show(getFragmentManager(), "saveToDatabase");


                    }
                }
        );
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(NitrateResultsActivity.this, CameraActivity.class);
        startActivity(intent);
    }

    public static class SaveToDatabaseDialogFragment extends DialogFragment {
        private String name,location, date, description, image;
        private Double latitude, longitude, nitrate, nitrite;

        public SaveToDatabaseDialogFragment() {
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            final LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            final View v = inflater.inflate(R.layout.save_to_datebase_dialog, null);
            builder.setView(v)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //TODO: show error if string is null

                            EditText nameEditText = (EditText) v.findViewById(R.id.nameEditText);
                            name = nameEditText.getText().toString();


                            Bundle bundle = getArguments();
                            location = bundle.getString("location");
                            date = bundle.getString("date");
                            description = bundle.getString("description");
                            image = bundle.getString("image");
                            longitude = bundle.getDouble("longitude");
                            latitude = bundle.getDouble("latitude");
                            nitrate = bundle.getDouble("nitrate");
                            nitrite = bundle.getDouble("nitrite");
                            NitrateResultsActivity n = new NitrateResultsActivity();
                            Database db = new Database(n);
                            if(db != null){
                                System.out.print("Here");
                            }
                            long id1 = db.saveNitrateReport(name, location, latitude, longitude, date, description, image, nitrate, nitrite);
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SaveToDatabaseDialogFragment.this.getDialog().cancel();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

}
