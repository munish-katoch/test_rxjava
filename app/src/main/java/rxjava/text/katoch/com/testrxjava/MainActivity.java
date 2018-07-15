package rxjava.text.katoch.com.testrxjava;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Observable mObservable = Observable      //Observable. This will emit the data
            .just("1","2","3");    //Operator

    Observer<String> mObserver = new Observer<String>() {
        @Override
        public void onError(Throwable e) {
            //...
            Log.d(TAG," onError: " + e.toString());
        }

        @Override
        public void onComplete() {
            Log.d(TAG," onComplete ");
        }


        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG," onSubscribe: " +d.toString());
        }

        @Override
        public void onNext(String s) {
            //...
            Log.d(TAG," onNext: " +s);
            Log.d(TAG," Current Thread: " +Thread.currentThread().toString());

        }
    };

    Subscription mSubscription = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Log.d(TAG," Main Thread: " +Thread.currentThread().toString());
        mObservable.subscribeOn(Schedulers.newThread())      ;                                  //Observable runs on new background thread.
        mObservable.observeOn(AndroidSchedulers.mainThread())    ;                              //Observer will run on main UI thread.
        mObservable.subscribe(mObserver);                                                        //Subscribe the observer
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
