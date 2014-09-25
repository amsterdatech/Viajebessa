package com.bemobi.viajebessa.infrastructure;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;

/**
 * Implementation of volley's ImageCache interface. This manager tracks the
 * application image loader and cache.
 * 
 * Volley recommends an L1 non-blocking cache which is the default MEMORY LRU
 * CACHE.
 * 
 * @author William Gouvea
 * 
 */
public class ImageCacheManager {
	private final String TAG = getClass().getSimpleName();
	/**
	 * Volley recommends in-memory L1 cache but both a disk and memory cache are
	 * provided.
	 */

	private static ImageCacheManager mInstance;

	/**
	 * Volley image loader
	 */
	private ImageLoader mImageLoader;

	/**
	 * Image cache implementation
	 */
	private ImageCache mImageCache;

	/**
	 * @return instance of the cache manager
	 */
	public static ImageCacheManager getInstance() {
		if (mInstance == null)
			mInstance = new ImageCacheManager();

		return mInstance;
	}

	/**
	 * Initializer for the manager. Must be called prior to use.
	 * 
	 * @param context
	 *            application context
	 * @param uniqueName
	 *            name for the cache location
	 * @param cacheSize
	 *            max size for the cache
	 * @param compressFormat
	 *            file type compression format.
	 * @param quality
	 */
	public void init() {

		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		Log.d(TAG, "Max Memory: " + maxMemory);
		Log.i(TAG, "Cache Size: " + maxMemory / 8);

		// 1/8th of app memory usually around 8mb
		mImageCache = new BitmapMemoryLruCache(maxMemory / 8);

		mImageLoader = new ImageLoader(RequestManager.getRequestQueue(),
				mImageCache);
	}

	/**
	 * Executes and image load
	 * 
	 * @param url
	 *            location of image
	 * @param listener
	 *            Listener for completion
	 */
	public void getImage(String url, ImageListener listener) {
		mImageLoader.get(url, listener);
	}

	/**
	 * @return instance of the image loader
	 */
	public ImageLoader getImageLoader() {
		return mImageLoader;
	}

	public static class BitmapMemoryLruCache extends LruCache<String, Bitmap>
			implements ImageCache {

		private final String TAG = getClass().getSimpleName();

		private int size;

		public BitmapMemoryLruCache(int cacheSize) {
			super(cacheSize);
		}

		@Override
		protected int sizeOf(String key, Bitmap value) {
			return value.getRowBytes() * value.getHeight();
		}

		@Override
		public Bitmap getBitmap(String url) {
			Log.v(TAG, "Retrieved item from Mem Cache");
			return get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bmp) {
			Log.v(TAG, "Added item to Mem Cache");
			put(url, bmp);
		}
	}

	/**
	 * Note: Volley decides whether to cache response or not, based only on
	 * headers "Cache-Control" and "Expires"
	 */

	public void getImageBitmap(String url, Listener<Bitmap> listener, int w,
			int h, ErrorListener error) {

		ImageRequest imgRequest = new ImageRequest(url, listener, w, h,
				Config.ARGB_8888, error);

		RequestManager.getRequestQueue().add(imgRequest);

	}

}
