package userBeans;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;

/**
 * This class manages the details of creating a Storage service, including auth.
 */
// [START authentication_application_default_credentials]
public class StorageFactory {
  private static Storage instance = null;

  public static synchronized Storage getService() throws IOException, GeneralSecurityException {
    if (instance == null) {
      instance = buildService();
    }
    return instance;
  }

  private static Storage buildService() throws IOException, GeneralSecurityException {
    HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
    JsonFactory jsonFactory = new JacksonFactory();
    GoogleCredential credential = GoogleCredential.getApplicationDefault(transport, jsonFactory);

    // Depending on the environment that provides the default credentials (for
    // example: Compute Engine, App Engine), the credentials may require us to
    // specify the scopes we need explicitly.  Check for this case, and inject
    // the Cloud Storage scope if required.
    if (credential.createScopedRequired()) {
      Collection<String> scopes = StorageScopes.all();
      credential = credential.createScoped(scopes);
    }

    return new Storage.Builder(transport, jsonFactory, credential)
        .setApplicationName("GCS Samples")
        .build();
  }
}
// [END authentication_application_default_credentials]
