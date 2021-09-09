package keycloak.social.edevlet;

import org.keycloak.broker.oidc.mappers.AbstractJsonUserAttributeMapper;

/**
 * User attribute mapper.
 * 
 * @author Vlastimil Elias (velias at redhat dot com)
 */
public class EDevletUserAttributeMapper extends AbstractJsonUserAttributeMapper {

	private static final String[] cp = new String[] { EDevletIdentityProviderFactory.PROVIDER_ID };

	@Override
	public String[] getCompatibleProviders() {
		return cp;
	}

	@Override
	public String getId() {
		return "edevlet-user-attribute-mapper";
	}

}