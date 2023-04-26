package vn.kien.event.eventbe.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryUtils {
    private static String CLOUDINARY_NAME = "dn3plmbec";
    private static String CLOUDINARY_API_KEY = "198242491911388";
    private static String CLOUDINARY_API_SECRET = "mspvmqqSqJoGgIKumFD3lot8Crk";

    public static Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUDINARY_NAME, // insert here you cloud name
                "api_key", CLOUDINARY_API_KEY, // insert here your api code
                "api_secret", CLOUDINARY_API_SECRET,
                "secure", true));
    }
}
