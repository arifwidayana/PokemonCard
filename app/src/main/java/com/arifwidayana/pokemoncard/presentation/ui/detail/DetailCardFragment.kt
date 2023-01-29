package com.arifwidayana.pokemoncard.presentation.ui.detail

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.arifwidayana.pokemoncard.common.base.BaseFragment
import com.arifwidayana.pokemoncard.common.utils.ImageUtils.downloadAndCompressImage
import com.arifwidayana.pokemoncard.common.wrapper.Resource
import com.arifwidayana.pokemoncard.data.network.model.response.CardPokemonResponse
import com.arifwidayana.pokemoncard.databinding.FragmentDetailCardBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailCardFragment : BaseFragment<FragmentDetailCardBinding, DetailCardViewModel>(
    FragmentDetailCardBinding::inflate
) {
    private val args by navArgs<DetailCardFragmentArgs>()
    private lateinit var pathImage: String

    override fun initView() {
        onView()
        onClick()
    }

    private fun onView() {
        viewModel.detailPokemon(args.id)
    }

    private fun onClick() {
        binding.apply {
            btnDownload.setOnClickListener {
                requestWriteStoragePermission()
                requireContext().registerReceiver(
                    onComplete,
                    IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
                )
            }
        }
    }

    override fun observeData() {
        lifecycleScope.apply {
            launchWhenStarted {
                viewModel.detailPokemonResult.collect {
                    if (it is Resource.Success) {
                        setStateView(it.data)
                        Log.d("TAG", "observeData: ${it.data?.toString()}")
                    } else if (it is Resource.Error) {
                        Log.d("TAG", "observeData: ${it.exception}")
                    }
                }
            }
        }
    }

    private fun setStateView(data: CardPokemonResponse?) {
        binding.apply {
            data?.let {
                Glide.with(root)
                    .load(it.images?.large)
                    .into(sivLargeImage)
                tvNamePokemon.text = it.name
                tvTypePokemon.text = it.types.joinToString()
                it.images?.large?.let { res ->
                    pathImage = res
                }
            }
        }
    }

    private val writeStoragePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            downloadAndCompressImage(requireContext(), pathImage)
        } else {
            showMessageSnackBar(true, "Permission Denied")
        }
    }

    private fun requestWriteStoragePermission() {
        writeStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private val onComplete = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            showMessageSnackBar(true, "Download complete")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(onComplete)
    }
}