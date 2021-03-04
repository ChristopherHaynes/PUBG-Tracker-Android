package wxc.pubgtracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

public class ChangeAPIKEYDialogFragment extends DialogFragment {

    PUBGManager manager;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        manager = MyProperties.getInstance().pubgManager;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter New API KEY");

        // Set up the input
        final EditText input = new EditText(getContext());
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SaveVariable("apiKey", input.getText().toString());

                SharedPreferences preferences;
                preferences = getActivity().getSharedPreferences("userprefs", 0);
                manager.apiKey = preferences.getString("apiKey", manager.defaultApiKey);

                dialog.dismiss();

                Snackbar changedAPIPopup = Snackbar.make(getActivity().findViewById(R.id.container), "API KEY has been updated, Restart application to apply changes", Snackbar.LENGTH_LONG);
                changedAPIPopup.show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder.show();
    }

    protected void SaveVariable(String tag, String value) {
        SharedPreferences preferences;
        preferences = getActivity().getSharedPreferences("userprefs", 0);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString(tag, value).commit();
    }
}
