package ch.whip.round.report;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dastep on 3/5/2016.
 */
@RestController
public class MapsReportController {

    //Pending deactivation
    //pft80TQ2kRomHCtZzE9EfBl24lo=
    //Will be deactivated Mar 6, 2016, 11:56:06 PM

    private String StaticMapsUrl = "https://maps.googleapis.com/maps/api/staticmap?key_or_client=weWIpamedSPCmPgdPm-vVVr1bxI=&maptype=roadmap";
    private String QueryImageParams = "&size=600x300&format=jpg";
    private String QueryCenter = "center=Brooklyn+Bridge,New+York,NY";
    private String QueryMarkers = "&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318" +
            "&markers=color:red%7Clabel:C%7C40.718217,-73.998284";

    @RequestMapping(path = "/report/map/{reportId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> mapReport(@PathVariable int reportId) {
        try {
            BufferedImage image = ImageIO.read(new URL(StaticMapsUrl + QueryImageParams + QueryCenter + QueryMarkers));

            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            try{
                ImageIO.write(image, "jpg", out);
                final byte[] imageData = out.toByteArray();
                final InputStream bigInputStream = new ByteArrayInputStream(imageData);

                final HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_PNG);

                return new ResponseEntity<byte[]>(IOUtils.toByteArray(bigInputStream), headers, HttpStatus.CREATED);

            }catch (final IOException ex)
            {
                ex.printStackTrace();
                return new ResponseEntity<byte[]>(HttpStatus.NO_CONTENT);
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
            return new ResponseEntity<byte[]>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<byte[]>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(path = "/report/link/{reportId}", method = RequestMethod.GET)
    public String linkReport(@PathVariable int reportId)
    {
        try
        {
            return new URL(StaticMapsUrl + QueryImageParams + QueryCenter + QueryMarkers).toString();
        }
        catch (MalformedURLException ex)
        {
            throw new IllegalStateException("Map url is malformed");
        }
    }
}
