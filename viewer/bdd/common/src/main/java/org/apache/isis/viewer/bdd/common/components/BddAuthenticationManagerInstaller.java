package org.apache.isis.viewer.bdd.common.components;

import org.apache.isis.core.metamodel.config.IsisConfiguration;
import org.apache.isis.core.runtime.authentication.standard.AuthenticationManagerStandardInstallerAbstract;
import org.apache.isis.core.runtime.authentication.standard.Authenticator;
import org.apache.isis.defaults.security.authentication.AuthenticatorNoop;

public class BddAuthenticationManagerInstaller extends
        AuthenticationManagerStandardInstallerAbstract {

    public BddAuthenticationManagerInstaller() {
        super("bdd");
    }

    @Override
    protected Authenticator createAuthenticator(
            final IsisConfiguration configuration) {
        return new AuthenticatorNoop(configuration);
    }
}