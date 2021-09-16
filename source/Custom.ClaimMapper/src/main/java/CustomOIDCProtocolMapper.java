import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.oidc.OIDCLoginProtocol;
import org.keycloak.protocol.oidc.mappers.*;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.AccessToken;
import org.keycloak.provider.ProviderConfigurationBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class CustomOIDCProtocolMapper extends AbstractOIDCProtocolMapper
        implements OIDCAccessTokenMapper, OIDCIDTokenMapper, UserInfoTokenMapper {

    public static final String PROVIDER_ID = "oidc-customprotocolmapper";

    private static final List<ProviderConfigProperty> configProperties;
    public static final String MAPPER_TYPE = "mappertype";
    public static final String DB_HOST_KEY = "db:host";
    public static final String DB_DATABASE_KEY = "db:database";
    public static final String DB_USERNAME_KEY = "db:username";
    public static final String DB_PASSWORD_KEY = "db:password";
    public static final String DB_PORT_KEY = "db:port";
    /**
     * Maybe you want to have config fields for your Mapper
     */
    
      static { 
        configProperties = ProviderConfigurationBuilder.create()
                // AttributeType
                // Not Implemented Yet :)
                .property().name(MAPPER_TYPE)
                .type(ProviderConfigProperty.LIST_TYPE)
                .label("Mapping Type")
                .helpText("Attribute type to add JWT Token")
                .defaultValue("Profile")
                .options("Roles", "Projects", "User Code", "Identity Number")
                .add()
                // Connection Host
                .property().name(DB_HOST_KEY)
                .type(ProviderConfigProperty.STRING_TYPE)
                .label("Database Host")
                .defaultValue("localhost")
                .helpText("Host of the connection")
                .add()

                // Connection Database
                .property().name(DB_DATABASE_KEY)
                .type(ProviderConfigProperty.STRING_TYPE)
                .label("Database Name")
                .defaultValue("")
                .add()

                // DB Username
                .property().name(DB_USERNAME_KEY)
                .type(ProviderConfigProperty.STRING_TYPE)
                .label("Database Username")
                .add()

                // DB Password
                .property().name(DB_PASSWORD_KEY)
                .type(ProviderConfigProperty.PASSWORD)
                .label("Database Password")
                .add()

                // DB Port
                .property().name(DB_PORT_KEY)
                .type(ProviderConfigProperty.STRING_TYPE)
                .label("Database Port")
                .defaultValue("1433")
                .add()
                .build();
      }
     
    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getDisplayCategory() {
        return "Token Mapper";
    }

    @Override
    public String getDisplayType() {
        return "Claim Mapper";
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getHelpText() {
        return "This custom mapper provides custom role claims for JWT token.";
    }

    // public IDToken transformAccessToken(IDToken token, ProtocolMapperModel
    // mappingModel, KeycloakSession keycloakSession,
    // UserSessionModel userSession, ClientSessionContext clientSessionCtx) {
    //// IDToken token, ProtocolMapperModel mappingModel, UserSessionModel
    // userSession, KeycloakSession keycloakSession, ClientSessionContext
    // clientSessionCtx
    // token.getOtherClaims().put("stackoverflowCustomToken", "stackoverflow");
    //// token.getOtherClaims().
    // // call profile API and enrich with permission claims
    //// setClaim(token, mappingModel, userSession);
    // setClaim(token, mappingModel, userSession, keycloakSession,clientSessionCtx);
    // return token;
    // }

    public AccessToken transformAccessToken(final AccessToken token, final ProtocolMapperModel mappingModel,
            final KeycloakSession keycloakSession, final UserSessionModel userSession,
            final ClientSessionContext clientSessionCtx) {
        Connection conn = null;
        
        try {

            String dbURL = "jdbc:sqlserver://"+mappingModel.getConfig().get(DB_HOST_KEY)+":"+mappingModel.getConfig().get(DB_PORT_KEY)+";databaseName="+mappingModel.getConfig().get(DB_DATABASE_KEY);
            String user = mappingModel.getConfig().get(DB_USERNAME_KEY);
            String pass = mappingModel.getConfig().get(DB_PASSWORD_KEY);
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs;
                if(mappingModel.getConfig().get(MAPPER_TYPE).equals("Roles")){
                    // AccessToken.Access roles = new AccessToken.Access();
                    rs = stmt.executeQuery("SELECT Personnel WHERE UserName = '"+userSession.getLoginUsername()+"'");
                    List<String> roleArray = new ArrayList<>();
                    while (rs.next()) {
                        String role = rs.getString("LINK");
                        System.out.println(role);
                        String[] roleIds = role.split(";|\\,");
                        for(int i = 0;i < roleIds.length;i++)
                            roleArray.add(roleIds[i]);
                    }
                    token.getOtherClaims().put("user_roles", roleArray.toArray());
                    // HashMap<String, AccessToken.Access> map = new HashMap<String, AccessToken.Access>();
                    // map.put("user_roles", roles);
                    // token.setResourceAccess(map);
                }
                if(mappingModel.getConfig().get(MAPPER_TYPE).equals("Projects")){
                    // AccessToken.Access roles2 = new AccessToken.Access();
                    Statement stmt2 = conn.createStatement();
                    ResultSet rs2 = stmt.executeQuery("SELECT Personnel WHERE UserName = '"+userSession.getLoginUsername()+"'");
                    List<String> projectArray = new ArrayList<>();
                    while (rs2.next()) {
                        String project = rs2.getString("Proje");
                        // System.out.println(project);
                        projectArray.add(project);
                    }
                    token.getOtherClaims().put("user_projects", projectArray.toArray());
                    // HashMap<String, AccessToken.Access> map2 = new HashMap<String, AccessToken.Access>();
                    // map2.put("user_projects", roles2);
                    // token.setResourceAccess(map2);
                }
                if(mappingModel.getConfig().get(MAPPER_TYPE).equals("User Code")){
                    token.getOtherClaims().put("user_code", "UserCode");
                }

                if(mappingModel.getConfig().get(MAPPER_TYPE).equals("Identity Number")){
                    Statement stmt2 = conn.createStatement();
                    ResultSet rs2 = stmt.executeQuery("SELECT Personnel WHERE UserName = '"+userSession.getLoginUsername()+"'");
                    String identityNumber = "";
                    while (rs2.next()) {
                        identityNumber = rs2.getString("PerNum");
                    }

                    token.getOtherClaims().put("identity_number", identityNumber);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println(userSession.getLoginUsername());
        System.out.println(clientSessionCtx.getClientScopeIds());
        // token.getOtherClaims().put("supiri", "token_supiri");
        
        // roles.addRole("role1");
        // roles.addRole("role2");
        // roles.addRole("role3");
        // roles.addRole("role4");
        // HashMap<String, AccessToken.Access> map = new HashMap<String, AccessToken.Access>();
        // map.put("test", roles);

        // token.setResourceAccess(map);
        setClaim(token, mappingModel, userSession, keycloakSession, clientSessionCtx);
        return token;
    }

    // public static ProtocolMapperModel create(String name, boolean accessToken,
    // boolean idToken, boolean userInfo) {
    // ProtocolMapperModel mapper = new ProtocolMapperModel();
    // mapper.setName(name);
    // mapper.setProtocolMapper(PROVIDER_ID);
    // mapper.setProtocol(OIDCLoginProtocol.LOGIN_PROTOCOL);
    // Map<String, String> config = new HashMap<String, String>();
    // config.put("id.token.claim", "true");
    // config.put("access.token.claim", "true");
    // mapper.setConfig(config);
    // return mapper;
    // }

    public static ProtocolMapperModel create(final String name, final boolean accessToken, final boolean idToken,
            final boolean userInfo) {
        final ProtocolMapperModel mapper = new ProtocolMapperModel();
        mapper.setName(name);
        mapper.setProtocolMapper(PROVIDER_ID);
        mapper.setProtocol(OIDCLoginProtocol.LOGIN_PROTOCOL);
        final Map<String, String> config = new HashMap<String, String>();
        // config.put(OIDCAttributeMapperHelper.TOKEN_CLAIM_NAME, hardcodedName);
        // config.put(CLAIM_VALUE, hardcodedValue);
        // config.put(OIDCAttributeMapperHelper.JSON_TYPE, claimType);
        config.put(OIDCAttributeMapperHelper.INCLUDE_IN_ACCESS_TOKEN, "true");
        config.put(OIDCAttributeMapperHelper.INCLUDE_IN_ID_TOKEN, "true");
        mapper.setConfig(config);
        return mapper;
    }

}
