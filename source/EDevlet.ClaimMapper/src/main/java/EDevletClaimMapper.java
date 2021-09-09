
import org.keycloak.broker.oidc.OIDCIdentityProviderFactory;
import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.broker.provider.IdentityBrokerException;
import org.keycloak.broker.provider.IdentityProviderMapper;
import org.keycloak.models.IdentityProviderMapperModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.provider.ProviderConfigProperty;
import org.apache.http.client.utils.URIBuilder;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Nikko Ambroselli
 * @version $Revision: 1 $
 */
public class EDevletClaimMapper extends AbstractProviderMapper {
    public static final String[] COMPATIBLE_PROVIDERS = { OIDCIdentityProviderFactory.PROVIDER_ID };

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<ProviderConfigProperty>();

    public static final String ATTRIBUTE_NAME = "attribute.name";
    public static final String ATTRIBUTE_FRIENDLY_NAME = "attribute.friendly.name";
    public static final String ATTRIBUTE_VALUE = "attribute.value";

    static {
        // ProviderConfigProperty property;
        // property = new ProviderConfigProperty();
        // property.setName(ATTRIBUTE_NAME);
        // property.setLabel("Attribute Name");
        // property.setHelpText(
        // "Name of attribute to search for in assertion. You can leave this blank and
        // specify a friendly name instead.");
        // property.setType(ProviderConfigProperty.LIST_TYPE);
        // property.setOptions(Arrays.asList(new String[] { "TC Kimlik No", "Ad Soyad"
        // }));
        // configProperties.add(property);
        // property = new ProviderConfigProperty();
        // property.setName(ATTRIBUTE_FRIENDLY_NAME);
        // property.setLabel("Friendly Name");
        // property.setHelpText(
        // "Friendly name of attribute to search for in assertion. You can leave this
        // blank and specify a name instead.");
        // property.setType(ProviderConfigProperty.STRING_TYPE);
        // configProperties.add(property);
        // property = new ProviderConfigProperty();
        // property.setName(ATTRIBUTE_VALUE);
        // property.setLabel("Attribute Value");
        // property.setHelpText(
        // "Value the attribute must have. If the attribute is a list, then the value
        // must be contained in the list.");
        // property.setType(ProviderConfigProperty.STRING_TYPE);
        // configProperties.add(property);
        // property = new ProviderConfigProperty();
        // property.setName("group");
        // property.setLabel("Group");
        // property.setHelpText("Group to grant to user. i.e. /Group1/SubGroup2");
        // property.setType(ProviderConfigProperty.STRING_TYPE);
        // configProperties.add(property);
    }

    public static final String PROVIDER_ID = "edevlet-idp-mapper";

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String[] getCompatibleProviders() {
        return COMPATIBLE_PROVIDERS;
    }

    @Override
    public String getDisplayCategory() {
        return "E Devlet Mapper";
    }

    @Override
    public String getDisplayType() {
        return "E Devlet Mapper Group";
    }

    @Override
    public void importNewUser(KeycloakSession session, RealmModel realm, UserModel user,
            IdentityProviderMapperModel mapperModel, BrokeredIdentityContext context) {
        // try {



            // // Map<String, String> parameters = new HashMap<>();
            // // parameters.put("clientId", "");
            // // parameters.put("accessToken", accessToken);
            // // parameters.put("resourceId", "1");
            // // parameters.put("kapsam", "Ad-Soyad");
            // // String form = parameters.keySet().stream()
            // //         .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
            // //         .collect(Collectors.joining("&"));
            // // HttpClient client = HttpClient.newHttpClient();
            // // HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://giris.build.turkiye.gov.tr/OAuth2AuthorizationServer/AuthenticationController"))
            // //         .headers("Content-Type", "application/x-www-form-urlencoded")
            // //         .POST(BodyPublishers.ofString(form)).build();
            // // HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // // // Process Response
            // // JsonNode root = new ObjectMapper().readTree(response.body());
            // // System.out.println(root.toPrettyString());
            // // JsonNode kullanici = root.get("kullaniciBilgileri");
            // // ad=kullanici.get("ad").asText();
            // // soyad=kullanici.get("soyad").asText();
            // // kimliNo=kullanici.get("kimlikNo").asText();
            
        //     HttpClient httpClient = HttpClient.newHttpClient();
        //     URIBuilder uriBuilder;
        //     uriBuilder = new URIBuilder("http://gw.esb.turkiye.gov.tr:8282/oauth/ResourceController");
        //     URI uri = uriBuilder.build();
        //     HttpRequest.Builder builder = HttpRequest.newBuilder().uri(uri);
        //     builder.POST(BodyPublishers.ofString("{\"clientId\": \"jandarma\",\"accessToken\":\"" + context.getToken()
        //             + "\",\"resourceId\":1,\"kapsam\":\"Ad-Soyad\"}"));
        //     builder.header(HttpHeaders.AUTHORIZATION, context.getToken());
        //     HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());

        //     // Process Response
        //     JsonNode root = new ObjectMapper().readTree(response.body());
        //     JsonNode kullanici = root.get("kullaniciBilgileri");
        //     user.setSingleAttribute("tc-kimlik-no", kullanici.get("kimlikNo").asText());
        //     user.setSingleAttribute("ad-soyad", kullanici.get("ad").asText() + " " + kullanici.get("soyad").asText());

        // } catch (URISyntaxException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // } catch (InterruptedException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        System.out.println(context.getToken());
        user.setSingleAttribute("tc-kimlik-no", "11111111110");
    }

    protected boolean isAttributePresent(IdentityProviderMapperModel mapperModel, BrokeredIdentityContext context) {
        return true;
    }

    @Override
    public void updateBrokeredUser(KeycloakSession session, RealmModel realm, UserModel user,
            IdentityProviderMapperModel mapperModel, BrokeredIdentityContext context) {
        // try {
        // HttpClient httpClient = HttpClient.newHttpClient();
        // URIBuilder uriBuilder;
        // uriBuilder = new
        // URIBuilder("http://gw.esb.turkiye.gov.tr:8282/oauth/ResourceController");
        // URI uri = uriBuilder.build();
        // HttpRequest.Builder builder = HttpRequest.newBuilder().uri(uri);
        // builder.POST(BodyPublishers.ofString("{\"clientId\":
        // \"jandarma\",\"accessToken\":\"" + context.getToken()
        // + "\",\"resourceId\":1,\"kapsam\":\"Ad-Soyad\"}"));
        // builder.header(HttpHeaders.AUTHORIZATION, context.getToken());
        // HttpResponse<String> response = httpClient.send(builder.build(),
        // HttpResponse.BodyHandlers.ofString());

        // // Process Response
        // JsonNode root = new ObjectMapper().readTree(response.body());
        // JsonNode kullanici = root.get("kullaniciBilgileri");
        // user.setSingleAttribute("tc-kimlik-no", kullanici.get("kimlikNo").asText());
        // user.setSingleAttribute("ad-soyad", kullanici.get("ad").asText() + " " +
        // kullanici.get("soyad").asText());

        // } catch (URISyntaxException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        System.out.println(context.getToken());
        user.setSingleAttribute("tc-kimlik-no", "11111111110");
    }

    @Override
    public String getHelpText() {
        return "If a claim exists, grant the user the specified realm or application group.";
    }

    @Override
    public EDevletClaimMapper create(KeycloakSession session) {
        return new EDevletClaimMapper();
    }

}