# Lokales Entiwckeln
Um die Google-Pub-Sub Java Bibliothek (lokal) verwenden zu können, muss eine Umgebungsvariable gesetzt werden:

Setting the environment variable
If you plan to use a service account with client library code, you need to set an environment variable.

Setting the environment variable allows you to provide credentials separately from your application, without making changes to application code when you deploy. Alternately, you can explicitly specify the path to the service account key file in your code. For more information, see the production guide.

Siehe Authentifizierung-Guide:
https://cloud.google.com/docs/authentication/getting-started

Es muss jeder Nutzer für einen Dinestschlüssel erstellen, den downloaden und die Umgebungsvariable GOOGLE_APPLICATION_CREDENTIALS darauf verweisen.

# Wenn es auf Kubernetes läuft
Obtaining credentials on Compute Engine, Kubernetes Engine, App Engine flexible environment, and Cloud Functions
If your application runs on Compute Engine, Kubernetes Engine, the App Engine flexible environment, or Cloud Functions, you don't need to create your own service account. Compute Engine includes a default service account that is automatically created for you, and you can assign a different service account, per-instance, if needed. When you create a new instance, the instance is automatically enabled to run as the default service account and has a default set of authorization permissions. For more information, see Compute Engine default service account.

After you set up a service account, ADC can implicitly find your credentials without any need to change your code, as described in the section above. If you want to specifically use Compute Engine credentials, you can explicitly do so, as shown in the following code example.

Siehe:

https://cloud.google.com/docs/authentication/production#setting_the_environment_variable