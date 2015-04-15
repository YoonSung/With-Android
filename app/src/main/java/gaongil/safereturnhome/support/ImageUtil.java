package gaongil.safereturnhome.support;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.exception.saveImageFileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ImageUtil {

	public static void saveProfileImage(Context context, Bitmap image, String imageName) throws saveImageFileException {
		String filePath = context.getApplicationContext().getFilesDir().getPath();
		File file = new File(filePath + File.separator + imageName + Constant.IMAGE_EXTENSION);
		
		OutputStream out = null;
		
		boolean isSuccess = true;
		
		try {
			// Create File And Stream Interface
			file.createNewFile();
		    out = new FileOutputStream(file);
		    
			// Bitmap Image Compress With Sending Stream
		    if (!image.compress(Bitmap.CompressFormat.PNG, 85, out)) {
		    	// if save failed.
		    	isSuccess = false;
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    try {
		        out.close();
		    } catch (IOException e) {}
		}
		
		if (!isSuccess)
			throw new saveImageFileException("Save Profile Exception");
	}
	
	public static Drawable getProfileImage(Context context, String imageName) {
        File file = context.getFileStreamPath(imageName+Constant.IMAGE_EXTENSION);
        System.out.println("Is File Exists? : "+file.exists());

        if (file.exists())
            return Drawable.createFromPath(file.getAbsolutePath());
        else
            return context.getResources().getDrawable(R.drawable.ic_default_profile);
    }

	public static void setCircleImageToTargetView(ImageView targetView, Bitmap bitmap) {
		RoundedAvatarDrawable rondedAvatarImg = new RoundedAvatarDrawable(bitmap);
        targetView.setImageDrawable(new RoundedAvatarDrawable(rondedAvatarImg.getBitmap()));	
	}

	public static void setCircleImageToTargetView(ImageView targetView, Drawable profile) {
		setCircleImageToTargetView(targetView, ((BitmapDrawable)profile).getBitmap());
	}
	
}
