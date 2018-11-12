package es.source.code.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {


    ProgressBar progressBar;
    Context context;
    Button orderButton;

    public MyAsyncTask(Context context, ProgressBar progressBar, Button orderButton){
        this.context = context;
        this.progressBar = progressBar;
        this.orderButton = orderButton;
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        DelayOperator delayOperator = new DelayOperator();
        int i, total = 0;
        for (i = 0; i < 10; i ++){
            publishProgress(i * 10);
            progressBar.setProgress(i * 10);
            delayOperator.delay();
        }
        return total;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        progressBar.setVisibility(View.INVISIBLE);
        if (orderButton.getText().equals("结账")) {
            orderButton.setClickable(false);
        }
        Toast.makeText(context, "结账成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }
}
