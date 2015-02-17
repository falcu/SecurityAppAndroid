package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.builders.services.BuilderServiceDataCorrupted;
import com.example.guido.securityapp.commands.FinishCommand;
import com.example.guido.securityapp.commands.ForceFinishCommand;
import com.example.guido.securityapp.commands.NullCommand;
import com.example.guido.securityapp.exceptions.UnableToLoadGroupException;
import com.example.guido.securityapp.exceptions.UnableToLoadTokenException;
import com.example.guido.securityapp.factories.FactoryGroupConfigurationFragments;
import com.example.guido.securityapp.fragments.BaseFragmentOption;
import com.example.guido.securityapp.fragments.Option;
import com.example.guido.securityapp.interfaces.ICommand;
import com.example.guido.securityapp.interfaces.IFragmentExceptionHandler;
import com.example.guido.securityapp.interfaces.IFragmentOptions;
import com.example.guido.securityapp.interfaces.IFragmentResultHandler;
import com.example.guido.securityapp.interfaces.IProgressBar;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.services.ServiceDataCorrupted;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GroupConfigurationCreatorActivity extends Activity implements IFragmentResultHandler,ITaskHandler,IFragmentExceptionHandler {

    private IProgressBar progressBar;
    protected List<DoubleDispatch> doubleDispatchers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_configuration);
        progressBar = (IProgressBar) getFragmentManager().findFragmentById(R.id.progress_bar_fragment);
        progressBar.setControllableView(findViewById(R.id.group_configuration_form));
        doubleDispatchers = makeDoubleDispatchers();
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        letDispatchersDecideAction(null);
    }

    protected List<DoubleDispatch> makeDoubleDispatchers()
    {
        ArrayList<DoubleDispatch> dispatchers = new ArrayList<>();

        ForceFinishCommand forceFinishCommand = new ForceFinishCommand(this);
        QuitResultDoubleDispatch quitDispatcher = new QuitResultDoubleDispatch();
        quitDispatcher.setAction(forceFinishCommand);

        FinishCommand finishCommand = new FinishCommand(this);
        NotQuitResultDoubleDispatch notQuitDispatcher = new NotQuitResultDoubleDispatch();
        notQuitDispatcher.setAction(finishCommand);

        ExceptionDoubleDispatch exceptionDoubleDispatch = new ExceptionDoubleDispatch();
        exceptionDoubleDispatch.setAction(forceFinishCommand);

        DataCorruptedDoubleDispatch dataCorruptedDoubleDispatch = new DataCorruptedDoubleDispatch();
        dataCorruptedDoubleDispatch.setAction(forceFinishCommand);

        dispatchers.add(quitDispatcher);
        dispatchers.add(notQuitDispatcher);
        dispatchers.add(exceptionDoubleDispatch);
        dispatchers.add(dataCorruptedDoubleDispatch);

        return dispatchers;
    }

    private void initialize()
    {
        try
        {
            IFragmentOptions fragmentOptions = (IFragmentOptions) getFragmentManager().findFragmentById(R.id.options_fragment);
                Iterator<BaseFragmentOption> fragments = getFragments();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                while(fragments.hasNext())
                {
                    BaseFragmentOption fragment = fragments.next();
                    fragmentTransaction.add(R.id.group_configuration_options,fragment);
                    fragmentOptions.addOption(new Option(fragment.getDescription(),fragment.getKey()));
                }
                fragmentTransaction.commit();
        }
        catch (Exception e)
        {

        }
    }

    protected Iterator<BaseFragmentOption> getFragments()
    {
        try
        {
            return FactoryGroupConfigurationFragments.getCreatorFragments();
        }
        catch (Exception e)
        {
            //TODO HANDLE EXCEPTION
            return null;
        }
    }
    protected BaseFragmentOption getFragmentByKey(String key) {
        try
        {
            return FactoryGroupConfigurationFragments.getFragmentByKey(key);
        }
        catch (Exception e)
        {
            //TODO HANDLE EXCEPTION
            return null;
        }
    }


    @Override
    public void handle(Object data) {
        String key = (String) data;
        setFragmentsVisibility(key);

    }

    private void setFragmentsVisibility(String key)
    {
        BaseFragmentOption selectedFragment = getFragmentByKey(key);
        selectedFragment.show();
        Iterator<BaseFragmentOption> fragments = getFragments();
        while(fragments.hasNext())
        {
            BaseFragmentOption fragmentOption = fragments.next();
            if(fragmentOption.getIdentifier()!=selectedFragment.getIdentifier())
            {
                fragmentOption.hide();
            }
        }
    }


    @Override
    public void onPreExecute() {
        progressBar.showProgress(true);
    }

    @Override
    public void onPostExecute(TaskResult taskResult) {
        progressBar.showProgress(false);
        if(taskResult.isSuccesful())
        {
            letDispatchersDecideAction(taskResult.getIdentifier());
        }
    }

    protected void letDispatchersDecideAction(Object objectToAnalyze)
    {
        for(DoubleDispatch dispatch: doubleDispatchers)
        {
            dispatch.dispatch(objectToAnalyze);
        }
    }

    @Override
    public void onCancelled() {
        progressBar.showProgress(false);
    }

    @Override
    public void handle(Exception e) {
        letDispatchersDecideAction(e);
    }

    public void finishActivityWith(Intent intent)
    {
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public abstract class DoubleDispatch
    {
        protected ICommand action = new NullCommand();

        public abstract void dispatch(Object objetToAnalyze);

        public void setAction(ICommand action) {
            this.action = action;
        }
    }

    public class NotQuitResultDoubleDispatch extends DoubleDispatch
    {

        @Override
        public void dispatch(Object objectToAnalyze) {
            try
            {
                if(objectToAnalyze==null){return;}
                String maybeString = (String)  objectToAnalyze;
                String quitKey = MyApplication.getContext().getString(R.string.quit_group_action_key);
                if(!maybeString.equals(quitKey))
                {
                    action.execute();
                }
            }
            catch (ClassCastException e){}

        }
    }


    public class QuitResultDoubleDispatch extends DoubleDispatch
    {

        public void dispatch(Object objectToAnalyze)
        {
            try
            {
                if(objectToAnalyze==null){return;}
                String maybeString = (String)  objectToAnalyze;
                if(maybeString.equals(MyApplication.getContext().getString(R.string.quit_group_action_key)))
                {
                    action.execute();
                }
            }
            catch (ClassCastException e){}
        }
    }

    public class ExceptionDoubleDispatch extends DoubleDispatch
    {

        @Override
        public void dispatch(Object objectToAnalyze) {
            if(objectToAnalyze==null){return;}
            if(objectToAnalyze.getClass().equals(UnableToLoadGroupException.class) || objectToAnalyze.getClass().equals(UnableToLoadTokenException.class))
            {
                action.execute();
            }
        }
    }

    public class DataCorruptedDoubleDispatch extends DoubleDispatch
    {

        @Override
        public void dispatch(Object objetToAnalyze) {
            ServiceDataCorrupted service = BuilderServiceDataCorrupted.build();
            if(service.isDataCorrupted())
            {
                action.execute();
            }
        }
    }
}
