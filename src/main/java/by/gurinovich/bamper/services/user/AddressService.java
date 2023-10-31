package by.gurinovich.bamper.services.user;

import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.user.Address;
import by.gurinovich.bamper.props.GeocoderProperties;
import by.gurinovich.bamper.repositories.user.AddressRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepo addressRepo;
    private final GeocoderProperties geocoderProperties;
    private final RestTemplate restTemplate;

    public Address getById(Long id){
        return addressRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address with this not found!"));
    }

    public String getCoordinatesByAddress(String address) {
        String urlString = geocoderProperties.getUrl() + address +
                "&" +
                geocoderProperties.getFormat();
        ResponseEntity<String> response = restTemplate.getForEntity(urlString, String.class);
        return parseResponseToCoordinates(response);
    }

    private String parseResponseToCoordinates(ResponseEntity<String> response){
        String coordinates = "";
        try {
            JSONObject json  = new JSONObject(response.getBody());
            JSONObject featureMember = new JSONObject(json
                    .getJSONObject("response")
                    .getJSONObject("GeoObjectCollection")
                    .getJSONArray("featureMember")
                    .get(0).toString());
            coordinates = featureMember
                    .getJSONObject("GeoObject")
                    .getJSONObject("Point").getString("pos");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
//        String[] coords = coordinates.split(" ");
//        String temp = coords[0];
//        coords[0] = coords[1];
//        coords[1] = temp;
//        return String.join(", ", Arrays.asList(coords));
        return coordinates;
    }


//    public String getAddressByCoordinates(String coordinates){
//
//    }
}