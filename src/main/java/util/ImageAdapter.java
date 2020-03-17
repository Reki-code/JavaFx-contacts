package main.java.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import main.java.data.model.Person;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageAdapter extends XmlAdapter<String, Image> {
    private static final String DEFAULT_AVATAR = "DEFAULT";

    @Override
    public Image unmarshal(String s) throws Exception {
        if (s.equals(DEFAULT_AVATAR)) {
            return Person.getDefaultAvatar();
        }
        BASE64Decoder base64Decoder = new BASE64Decoder();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(base64Decoder.decodeBuffer(s));
        return new Image(inputStream);
    }

    @Override
    public String marshal(Image image) throws IOException {
        if (image.equals(Person.getDefaultAvatar())) {
            return DEFAULT_AVATAR;
        }
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteStream);
        return Base64.getEncoder().encodeToString(byteStream.toByteArray());
    }
}
