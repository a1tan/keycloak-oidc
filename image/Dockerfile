FROM quay.io/keycloak/keycloak:15.0.2

#Database Providers
COPY ./mssql-jdbc/ /opt/jboss/keycloak/modules/system/layers/base/com/microsoft/
COPY ./odbc/ /opt/jboss/keycloak/modules/system/layers/keycloak/com/oracle/

#Configuration
COPY ./configuration/ /opt/jboss/standalone/configuration/

#Custom Spis
COPY ./custom-identityprovider/ /opt/jboss/keycloak/standalone/deployments/
COPY ./custom-recaptcha/ /opt/jboss/keycloak/standalone/deployments/
COPY ./edevlet-claimmapper/ /opt/jboss/keycloak/standalone/deployments/
COPY ./edevlet-socialmedia/ /opt/jboss/keycloak/standalone/deployments/
COPY ./claim-mapper/ /opt/jboss/keycloak/standalone/deployments/

#Themes
COPY ./themes/ /opt/jboss/keycloak/themes/
