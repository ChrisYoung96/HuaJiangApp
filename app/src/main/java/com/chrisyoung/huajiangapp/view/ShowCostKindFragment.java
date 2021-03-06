package com.chrisyoung.huajiangapp.view;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.presenter.KindPresenter;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;
import com.chrisyoung.huajiangapp.view.adapter.ShowKindGridViewAdapter;
import com.chrisyoung.huajiangapp.view.vinterface.IKindView;
import com.chrisyoung.huajiangapp.view.vinterface.OnViewGenerateListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowCostKindFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowCostKindFragment extends BaseFragment implements IKindView, OnViewGenerateListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.costKindGridView)
    GridView costKindGridView;
    Unbinder unbinder;
    @BindView(R.id.btnAddCostKind)
    Button btnAddCostKind;
    @BindView(R.id.kindRefreshLayout1)
    QMUIPullRefreshLayout kindRefreshLayout1;

    // TODO: Rename and change types of parameters
    private static final String type="支出";
    private String uId;
    private String token;

    private KindPresenter presenter;

    private SwipeLayout swipeLayout;

    private ShowKindGridViewAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public ShowCostKindFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowCostKindFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowCostKindFragment newInstance(String param1, String param2) {
        ShowCostKindFragment fragment = new ShowCostKindFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            token = getArguments().getString(ARG_PARAM1);
            uId = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_cost_kind, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.closeRealm();
        unbinder.unbind();
    }

    private void init() {
        presenter = new KindPresenter(new MainActivity(),this);
        kindRefreshLayout1.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                kindRefreshLayout1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.synchKindDataFromServer(token,uId);
                        kindRefreshLayout1.finishRefresh();
                    }
                },1000);


            }
        });

        presenter.showKinds(uId, type);
    }


    @Override
    public void showResult(String result) {

    }

    @Override
    public void showKinds(ArrayList<CUserDiyKind> kinds) {
        if (kinds == null || kinds.isEmpty()) {
            ToastUtil.showShort(getContext(), "没有自定义类别记录");
        }
        adapter = new ShowKindGridViewAdapter(kinds, getContext(), this);
        adapter.setMode(Attributes.Mode.Multiple);
        costKindGridView.setAdapter(adapter);
        costKindGridView.setSelected(false);
        costKindGridView.setNumColumns(4);

    }

    @Override
    public void refreshUI() {
        init();
    }

    @Override
    public void initView(View v) {
        swipeLayout = v.findViewById(R.id.gridKindItemLayout);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada)
                        .duration(500)
                        .delay(100)
                        .playOn(layout.findViewById(R.id.btnDeleteKind));

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        v.findViewById(R.id.btnDeleteKind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cId = v.getTag().toString();
                if (presenter.deleteKind(cId)) {
                    init();
                }


            }
        });
    }

    @OnClick(R.id.btnAddCostKind)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), AddKindsActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("uId", uId);
        getContext().startActivity(intent);
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void hideErrorDialog() {

    }

    @Override
    public LifecycleTransformer bindLifecycle() {
        return bindToLifecycle();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
