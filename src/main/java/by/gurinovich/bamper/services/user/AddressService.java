package by.gurinovich.bamper.services.user;

import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.postsEntities.Post;
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
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepo addressRepo;
    private final GeocoderProperties geocoderProperties;
    private final RestTemplate restTemplate;

    @Transactional
    public Address save(Post post, String request){
        String coordinates = getCoordinatesByAddress(request);
        Optional<Address> check = addressRepo.findByCoordinates(coordinates);
        if (check.isPresent())
            return check.get();
        Address address = Address.builder()
                .coordinates(coordinates)
                .address(getAddressByCoordinates(coordinates))
                .post(post)
                .build();
        return addressRepo.save(address);
    }

    public Address getById(Long id){
        return addressRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address with this not found!"));
    }

    public String getCoordinatesByAddress(String address) {
        String url = generateUrl(address);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return parseResponseToCoordinates(response);
    }

    public String getAddressByCoordinates(String coordinates){
        String url = generateUrl(coordinates);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return parseResponseToAddress(response);
    }

    private String generateUrl(String geocode){
        String url = geocoderProperties.getUrl() + geocode +
                "&" +
                geocoderProperties.getFormat();
        return url;
    }

    private String parseResponseToCoordinates(ResponseEntity<String> response) {
        JSONObject json = new JSONObject(response.getBody());
        JSONObject featureMember = new JSONObject(json
                .getJSONObject("response")
                .getJSONObject("GeoObjectCollection")
                .getJSONArray("featureMember")
                .get(0).toString());
        String coordinates = featureMember
                .getJSONObject("GeoObject")
                .getJSONObject("Point").getString("pos");
        return coordinates;
    }


    private String parseResponseToAddress(ResponseEntity<String> response){
        JSONObject json = new JSONObject(response.getBody());
        JSONObject featureMember = new JSONObject(json
                .getJSONObject("response")
                .getJSONObject("GeoObjectCollection")
                .getJSONArray("featureMember")
                .get(0).toString());
        String address = featureMember
                .getJSONObject("GeoObject")
                .getJSONObject("metaDataProperty")
                .getJSONObject("GeocoderMetaData")
                .getString("text");
        return address;
    }


}