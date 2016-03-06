package ch.whip.round.report;

import ch.whip.round.account.GroupAccount;
import ch.whip.round.transaction.Transaction;
import ch.whip.round.transaction.TransactionService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by dastep on 3/5/2016.
 */
@RestController
@CrossOrigin(origins = "*")
public class MapsReportController {

    //Pending deactivation
    //pft80TQ2kRomHCtZzE9EfBl24lo=
    //Will be deactivated Mar 6, 2016, 11:56:06 PM

    @Autowired
    private TransactionService transactionService;

    private String StaticMapsUrl = "https://maps.googleapis.com/maps/api/staticmap?key_or_client=weWIpamedSPCmPgdPm-vVVr1bxI=&maptype=roadmap";
    private String QueryImageParams = "&size=600x300&format=jpg";
    private String QueryCenter = "center=Brooklyn+Bridge,New+York,NY";

    private String markerFormat = "&markers=color:red%7Clabel:";
    private String pathFormat = "path=color:0x0000ff|weight:5|40.737102,-73.990318|40.749825,-73.987963|40.752946,-73.987384|40.755823,-73.986397";
    //private String markerFormat = "&markers=color:red%7Clabel:S%7C";
    //private String QueryMarkers = "&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318" +
    //"&markers=color:red%7Clabel:C%7C40.718217,-73.998284";

    @RequestMapping(path = "/report/map", method = RequestMethod.GET)
    public ResponseEntity<byte[]> mapReport(@RequestParam GroupAccount group) {
        try {
            URL url = generateUrl(group);
            BufferedImage image = ImageIO.read(url);

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

    @RequestMapping(path = "/report/link", method = RequestMethod.GET)
    public String linkReport(@RequestParam GroupAccount group)
    {
        return generateUrl(group).toString();
    }

    public URL generateUrl(GroupAccount group) throws IllegalStateException
    {
        try
        {
            URL url = new URL(StaticMapsUrl + QueryImageParams + QueryCenter + generateMarkers(group) + generatePaths(group));
            return url;
        }
        catch (MalformedURLException ex)
        {
            throw new IllegalStateException("Map url is malformed");
        }
    }

    public String generatePaths(@RequestParam GroupAccount group)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("&path=color:0x0000ff|weight:5");
        List<Transaction> transactions = transactionService.findAccountTransactions(group);
        for (Transaction tr :transactions) {
            if(tr.getGeocode() != null && !tr.getGeocode().isEmpty())
            {
                sb.append("|").append(tr.getGeocode());
            }
        }
        return sb.toString();
    }

    public String generateMarkers(@RequestParam GroupAccount group)
    {
        StringBuilder sb = new StringBuilder();
        List<Transaction> transactions = transactionService.findAccountTransactions(group);
        for (Transaction tr :transactions) {
            if(tr.getGeocode() != null && !tr.getGeocode().isEmpty())
            {
                //sb.append(markerFormat).append(markerLabelFormat).append(tr.getDetails()).append("%7C").append(tr.getGeocode());
                sb.append(markerFormat).append(tr.getDetails()).append("%7C").append(tr.getGeocode());
            }
        }
        return sb.toString();
    }

}
