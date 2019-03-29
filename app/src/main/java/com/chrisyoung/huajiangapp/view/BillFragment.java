package com.chrisyoung.huajiangapp.view;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.constant.StatusCode;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.presenter.BillPresenter;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;
import com.chrisyoung.huajiangapp.view.adapter.ShowBillGridViewAdapter;
import com.chrisyoung.huajiangapp.view.vinterface.IAddBillView;
import com.chrisyoung.huajiangapp.view.vinterface.OnViewGenerateListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;

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
 * Use the {@link BillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillFragment extends BaseFragment implements IAddBillView ,OnViewGenerateListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.btnAddBill)
    ImageButton btnAddBill;
    @BindView(R.id.billGridView)
    GridView billGridView;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String uId;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private BillPresenter billPresenter;
    private ShowBillGridViewAdapter adapter;
    private SwipeLayout swipeLayout;
    private ArrayList<CBill>bills;



    public BillFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BillFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BillFragment newInstance(String param1, String param2) {
        BillFragment fragment = new BillFragment();
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
            //uId = getArguments().getString(ARG_PARAM1);
            uId="94d5f9cbd27b4526a9b90176f44037d7";
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init(){
        billPresenter=new BillPresenter(this);

        bills=billPresenter.showBills(uId);
        if(bills!=null){
            adapter=new ShowBillGridViewAdapter(bills,getContext(),this);
            adapter.setMode(Attributes.Mode.Multiple);
            billGridView.setAdapter(adapter);
            billGridView.setSelected(false);
            billGridView.setNumColumns(3);
            billGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String bId=bills.get(position).getbId();
                    Intent intent=new Intent(getContext(),ModifyBillActivity.class);
                    intent.putExtra("bId",bId);
                    intent.putExtra("action",StatusCode.UPDATE);
                    startActivity(intent);
                }
            });

        }else{
            ToastUtil.showShort(getContext(),"请添加账本");
        }

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
    public void showResult(String result) {
        ToastUtil.showShort(getContext(), result);
    }

    @Override
    public void resetUI() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnAddBill)
    public void onViewClicked() {
        Intent intent=new Intent(getContext(),ModifyBillActivity.class);
        intent.putExtra("uId",uId);
        intent.putExtra("anciton",StatusCode.INSERT);
        startActivity(intent);
    }

    @Override
    public void initView(View v) {
        swipeLayout=v.findViewById(R.id.gridItemLayout);
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
                        .playOn(layout.findViewById(R.id.btnDeleteBill));

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

        v.findViewById(R.id.btnDeleteBill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bid=v.getTag().toString();
                billPresenter.deleteBill(bid);
                init();

            }
        });

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
