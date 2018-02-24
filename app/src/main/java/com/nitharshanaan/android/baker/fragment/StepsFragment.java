package com.nitharshanaan.android.baker.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nitharshanaan.android.baker.R;
import com.nitharshanaan.android.baker.adapter.OnListFragmentInteractionListener;
import com.nitharshanaan.android.baker.adapter.StepRecyclerViewAdapter;
import com.nitharshanaan.android.baker.data.Recipe;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nitharshanaan.android.baker.utils.AppConstants.SELECTED_RECIPE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnListFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class StepsFragment extends Fragment {

    @BindView(R.id.steps_recycler_view)
    RecyclerView stepsRecyclerView;
    private OnListFragmentInteractionListener mListener;

    public StepsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this, view);

        Recipe selectedRecipe;
        if (this.getArguments().containsKey(SELECTED_RECIPE)) {
            selectedRecipe = Parcels.unwrap(this.getArguments().getParcelable(SELECTED_RECIPE));
            // Set the layout manager
            stepsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            stepsRecyclerView.setAdapter(new StepRecyclerViewAdapter(getContext(), mListener, selectedRecipe.getSteps()));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
