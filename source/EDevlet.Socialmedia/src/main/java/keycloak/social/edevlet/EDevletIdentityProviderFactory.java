package keycloak.social.edevlet;


import org.keycloak.broker.oidc.OAuth2IdentityProviderConfig;
import org.keycloak.broker.provider.AbstractIdentityProviderFactory;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.broker.social.SocialIdentityProviderFactory;
import org.keycloak.models.KeycloakSession;

/**
 * @author Vlastimil Elias (velias at redhat dot com)
 */
public class EDevletIdentityProviderFactory extends AbstractIdentityProviderFactory<EDevletIdentityProvider>
		implements SocialIdentityProviderFactory<EDevletIdentityProvider> {

    public EDevletIdentityProviderFactory(){
        super();
    }        
	public static final String PROVIDER_ID = "edevlet";

	@Override
	public String getName() {
		return "EDevlet";
	}

	@Override
	public EDevletIdentityProvider create(KeycloakSession session, IdentityProviderModel model) {
		return new EDevletIdentityProvider(session, new OAuth2IdentityProviderConfig(model));
	}

	@Override
	public OAuth2IdentityProviderConfig createConfig() {
		return new OAuth2IdentityProviderConfig();
	}

	@Override
	public String getId() {
		return PROVIDER_ID;
	}
}