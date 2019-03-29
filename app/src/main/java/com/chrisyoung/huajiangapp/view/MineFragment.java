package com.chrisyoung.huajiangapp.view;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chrisyoung.huajiangapp.R;
import com.qmuiteam.qmui.widget.QMUILoadingView;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.mine_icon)
    CircleImageView mineIcon;
    @BindView(R.id.mine_name)
    TextView mineName;
    @BindView(R.id.mineGroupListView)
    QMUIGroupListView mineGroupListView;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String uId;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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
            uId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        initGroupListView();
        return view;
    }


    private void initGroupListView() {
        QMUICommonListItemView itemSetKinds = mineGroupListView.createItemView(
                "类别设置");
        itemSetKinds.setOrientation(QMUICommonListItemView.VERTICAL);
        itemSetKinds.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);


        QMUICommonListItemView itemMine = mineGroupListView.createItemView("我的信息");
        itemMine.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView itemAutoSyncWithWIFI = mineGroupListView.createItemView("WIFI自动同步");
        itemAutoSyncWithWIFI.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        itemAutoSyncWithWIFI.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getActivity(), "checked = " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        QMUICommonListItemView itemSync = mineGroupListView.createItemView("同步数据");
        itemSync.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        QMUILoadingView loadingView = new QMUILoadingView(getActivity());
        itemSync.addAccessoryCustomView(loadingView);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof QMUICommonListItemView) {
                    CharSequence text = ((QMUICommonListItemView) v).getText();
                    if (text.equals("我的信息")) {
                        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                        intent.putExtra("uId",uId);
                        getContext().startActivity(intent);
                        getActivity().finish();
                    } else if (text.equals("类别设置")) {
                        Intent intent = new Intent(getActivity(), KindsActivity.class);
                        intent.putExtra("uId",uId);
                        getContext().startActivity(intent);
                        getActivity().finish();
                    } else if (((QMUICommonListItemView) v).getAccessoryType() == QMUICommonListItemView.ACCESSORY_TYPE_SWITCH) {
                        ((QMUICommonListItemView) v).getSwitch().toggle();
                    } else{
                        Toast.makeText(getActivity(), text + " is Clicked", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        };


        QMUIGroupListView.newSection(getContext())
                .addItemView(itemMine, onClickListener)
                .addItemView(itemSetKinds, onClickListener)
                .addItemView(itemAutoSyncWithWIFI, null)
                .addItemView(itemSync, onClickListener)
                .addTo(mineGroupListView);


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
        unbinder.unbind();
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
