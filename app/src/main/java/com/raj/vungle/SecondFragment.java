package com.raj.vungle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.raj.vungle.databinding.FragmentSecondBinding;
import com.vungle.warren.AdConfig;
import com.vungle.warren.BannerAdConfig;
import com.vungle.warren.Banners;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.VungleBanner;
import com.vungle.warren.error.VungleException;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private VungleBanner vungleBannerAd;
   // private FrameLayout bannerContainer =  getActivity().findViewById(R.id.container1_banner);
    private static final String LOG_TAG = "SecondFragement";
    private void makeToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    private final LoadAdCallback vungleLoadAdCallback = new LoadAdCallback() {
        @Override
        public void onAdLoad(String placementReferenceID) {
            // Ad has been successfully loaded for the placement
            Log.d(LOG_TAG, "LoadAdCallback onAdLoad" +  placementReferenceID);
            makeToast("Ad Loaded Click Play");
        }

        @Override
        public void onError(String placementReferenceID, VungleException exception) {
            // Ad has failed to load for the placement
            Log.d(LOG_TAG, "LoadAdCallback - onError" +
                    "\n\tPlacement Reference ID = " + placementReferenceID +
                    "\n\tError = " + exception.getLocalizedMessage());
        }
    };

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {
        //Fragment bannerContainer = requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();



    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        binding.buttonint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
// Load Ad Implementation
                if (Vungle.isInitialized()) {
                    Vungle.loadAd("DEFAULT-7488346", vungleLoadAdCallback);

                }

            }
        });
        binding.buttonplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Vungle.canPlayAd("DEFAULT-7488346")){
                    Vungle.playAd("DEFAULT-7488346", null, vunglePlayAdCallback);

                }
            }
        });

        binding.buttonbanner.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (Vungle.isInitialized()) {
                    final BannerAdConfig bannerAdConfig = new BannerAdConfig();
                    bannerAdConfig.setAdSize(AdConfig.AdSize.BANNER);
                    Banners.loadBanner("BANNER-8119187", bannerAdConfig, vungleLoadAdCallback);

                }
            }
        });

        binding.buttonplaybanner.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (Vungle.isInitialized()) {
                    final BannerAdConfig bannerAdConfig = new BannerAdConfig();
                    bannerAdConfig.setAdSize(AdConfig.AdSize.BANNER);
                    Banners.canPlayAd("BANNER-8119187", bannerAdConfig.getAdSize());
                    if (Banners.canPlayAd("BANNER-8119187", bannerAdConfig.getAdSize())) {


                        VungleBanner vungleBanner = Banners.getBanner("BANNER-8119187", bannerAdConfig, vunglePlayAdCallback);
Log.d(LOG_TAG, "vungleBanner" + vungleBanner);
                        //bannerContainer.addView(vungleBanner);
                        if (vungleBannerAd != null) {

                            FrameLayout   bannerContainer = (FrameLayout) view.findViewById(R.id.container_banner);
                            bannerContainer.addView(vungleBanner);
                            bannerContainer.setVisibility(View.VISIBLE);
                        }



                    }

                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        //vungleBannerAd.destroyAd();
    }


    private final PlayAdCallback vunglePlayAdCallback = new PlayAdCallback() {
        @Override
        public void onAdStart(String placementReferenceId) {
            // Ad experience started
        }

        @Override
        public void onAdEnd(String placementId, boolean completed, boolean isCTAClicked) {

        }

        @Override
        public void onAdViewed(String placementReferenceId) {
            // Ad has rendered
        }

        @Override
        public void onAdEnd(String placementReferenceId) {
            // Ad experience ended
        }

        @Override
        public void onAdClick(String placementReferenceId) {
            // User clicked on ad
        }

        @Override
        public void onAdRewarded(String placementReferenceId) {
            // User earned reward for watching an rewarded ad
        }

        @Override
        public void onAdLeftApplication(String placementReferenceId) {
            // User has left app during an ad experience
        }

        @Override
        public void creativeId(String creativeId) {
            // Vungle creative ID to be displayed
        }

        @Override
        public void onError(String placementReferenceID, VungleException exception) {
            // Ad failed to play
            Log.d(LOG_TAG, "LoadAdCallback - onError" +
                    "\n\tPlacement Reference ID = " + placementReferenceID +
                    "\n\tError = " + exception.getLocalizedMessage());
        }
    };

}


