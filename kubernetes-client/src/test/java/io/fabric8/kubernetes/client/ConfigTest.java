/**
 * Copyright (C) 2015 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.fabric8.kubernetes.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ConfigTest {

  @Before
  public void setUp() {
    System.getProperties().remove(Config.KUBERNETES_MASTER_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_NAMESPACE_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_OAUTH_TOKEN_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_AUTH_BASIC_USERNAME_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_AUTH_BASIC_PASSWORD_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_TRUST_CERT_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_CA_CERTIFICATE_FILE_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_CA_CERTIFICATE_DATA_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_CLIENT_CERTIFICATE_FILE_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_CLIENT_CERTIFICATE_DATA_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_CLIENT_KEY_FILE_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_CLIENT_KEY_DATA_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_CLIENT_KEY_ALGO_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_CLIENT_KEY_PASSPHRASE_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_CLIENT_KEY_FILE_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_WATCH_RECONNECT_INTERVAL_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_WATCH_RECONNECT_LIMIT_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_REQUEST_TIMEOUT_SYSTEM_PROPERTY);
    System.getProperties().remove(Config.KUBERNETES_HTTP_PROXY);
  }

  @After
  public void tearDown() {
    setUp();
  }

  @Test
  public void testWithSystemProperties() {
    System.setProperty(Config.KUBERNETES_MASTER_SYSTEM_PROPERTY, "http://somehost:80");
    System.setProperty(Config.KUBERNETES_NAMESPACE_SYSTEM_PROPERTY, "testns");

    System.setProperty(Config.KUBERNETES_OAUTH_TOKEN_SYSTEM_PROPERTY, "token");
    System.setProperty(Config.KUBERNETES_AUTH_BASIC_USERNAME_SYSTEM_PROPERTY, "user");
    System.setProperty(Config.KUBERNETES_AUTH_BASIC_PASSWORD_SYSTEM_PROPERTY, "pass");
    System.setProperty(Config.KUBERNETES_TRUST_CERT_SYSTEM_PROPERTY, "true");
    System.setProperty(Config.KUBERNETES_CA_CERTIFICATE_FILE_SYSTEM_PROPERTY, "/path/to/cert");
    System.setProperty(Config.KUBERNETES_CA_CERTIFICATE_DATA_SYSTEM_PROPERTY, "cacertdata");
    System.setProperty(Config.KUBERNETES_CLIENT_CERTIFICATE_FILE_SYSTEM_PROPERTY, "/path/to/clientcert");
    System.setProperty(Config.KUBERNETES_CLIENT_CERTIFICATE_DATA_SYSTEM_PROPERTY, "clientcertdata");
    System.setProperty(Config.KUBERNETES_CLIENT_KEY_FILE_SYSTEM_PROPERTY, "/path/to/clientkey");
    System.setProperty(Config.KUBERNETES_CLIENT_KEY_DATA_SYSTEM_PROPERTY, "clientkeydata");
    System.setProperty(Config.KUBERNETES_CLIENT_KEY_ALGO_SYSTEM_PROPERTY, "algo");
    System.setProperty(Config.KUBERNETES_CLIENT_KEY_PASSPHRASE_SYSTEM_PROPERTY, "passphrase");
    System.setProperty(Config.KUBERNETES_CLIENT_KEY_FILE_SYSTEM_PROPERTY, "/path/to/clientkey");
    System.setProperty(Config.KUBERNETES_WATCH_RECONNECT_INTERVAL_SYSTEM_PROPERTY, "5000");
    System.setProperty(Config.KUBERNETES_WATCH_RECONNECT_LIMIT_SYSTEM_PROPERTY, "5");
    System.setProperty(Config.KUBERNETES_REQUEST_TIMEOUT_SYSTEM_PROPERTY, "5000");
    System.setProperty(Config.KUBERNETES_HTTP_PROXY, "httpProxy");

    Config config = new Config();
    assertConfig(config);

    config = new ConfigBuilder().build();
    assertConfig(config);
  }

  @Test
  public void testWithBuilder() {
    Config config = new ConfigBuilder()
      .withMasterUrl("http://somehost:80")
      .withApiVersion("v1")
      .withNamespace("testns")
      .withOauthToken("token")
      .withUsername("user")
      .withPassword("pass")
      .withTrustCerts(true)
      .withCaCertFile("/path/to/cert")
      .withCaCertData("cacertdata")
      .withClientCertFile("/path/to/clientcert")
      .withClientCertData("clientcertdata")
      .withClientKeyFile("/path/to/clientkey")
      .withClientKeyData("clientkeydata")
      .withClientKeyAlgo("algo")
      .withClientKeyPassphrase("passphrase")
      .withWatchReconnectInterval(5000)
      .withWatchReconnectLimit(5)
      .withRequestTimeout(5000)
      .withProxy("httpProxy")
      .build();

    assertConfig(config);
  }


  @Test
  public void testWithBuilderAndSystemProperties() {
    System.setProperty(Config.KUBERNETES_MASTER_SYSTEM_PROPERTY, "http://tobeoverriden:80");
    System.setProperty(Config.KUBERNETES_NAMESPACE_SYSTEM_PROPERTY, "tobeoverriden");

    System.setProperty(Config.KUBERNETES_OAUTH_TOKEN_SYSTEM_PROPERTY, "token");
    System.setProperty(Config.KUBERNETES_AUTH_BASIC_USERNAME_SYSTEM_PROPERTY, "user");
    System.setProperty(Config.KUBERNETES_AUTH_BASIC_PASSWORD_SYSTEM_PROPERTY, "pass");
    System.setProperty(Config.KUBERNETES_TRUST_CERT_SYSTEM_PROPERTY, "true");
    System.setProperty(Config.KUBERNETES_CA_CERTIFICATE_FILE_SYSTEM_PROPERTY, "/path/to/cert");
    System.setProperty(Config.KUBERNETES_CA_CERTIFICATE_DATA_SYSTEM_PROPERTY, "cacertdata");
    System.setProperty(Config.KUBERNETES_CLIENT_CERTIFICATE_FILE_SYSTEM_PROPERTY, "/path/to/clientcert");
    System.setProperty(Config.KUBERNETES_CLIENT_CERTIFICATE_DATA_SYSTEM_PROPERTY, "clientcertdata");
    System.setProperty(Config.KUBERNETES_CLIENT_KEY_FILE_SYSTEM_PROPERTY, "/path/to/clientkey");
    System.setProperty(Config.KUBERNETES_CLIENT_KEY_DATA_SYSTEM_PROPERTY, "clientkeydata");
    System.setProperty(Config.KUBERNETES_CLIENT_KEY_ALGO_SYSTEM_PROPERTY, "algo");
    System.setProperty(Config.KUBERNETES_CLIENT_KEY_PASSPHRASE_SYSTEM_PROPERTY, "passphrase");
    System.setProperty(Config.KUBERNETES_CLIENT_KEY_FILE_SYSTEM_PROPERTY, "/path/to/clientkey");
    System.setProperty(Config.KUBERNETES_WATCH_RECONNECT_INTERVAL_SYSTEM_PROPERTY, "5000");
    System.setProperty(Config.KUBERNETES_WATCH_RECONNECT_LIMIT_SYSTEM_PROPERTY, "5");
    System.setProperty(Config.KUBERNETES_REQUEST_TIMEOUT_SYSTEM_PROPERTY, "5000");
    System.setProperty(Config.KUBERNETES_HTTP_PROXY, "httpProxy");

    Config config = new ConfigBuilder()
      .withMasterUrl("http://somehost:80")
      .withNamespace("testns")
      .build();

    assertConfig(config);
  }

  @Test
  public void testWithKubeConfig() {
    System.setProperty(Config.KUBERNETES_KUBECONFIG_FILE, getClass().getResource("/test-kubeconfig").getFile());
    Config config = new Config();
    assertNotNull(config);

    assertEquals("https://172.28.128.4:8443/", config.getMasterUrl());
    assertEquals("testns", config.getNamespace());
    assertEquals("token", config.getOauthToken());
  }

  @Test
  public void testWithKubeConfigAndSystemProperties() {
    System.setProperty(Config.KUBERNETES_KUBECONFIG_FILE, getClass().getResource("/test-kubeconfig").getFile());
    System.setProperty(Config.KUBERNETES_MASTER_SYSTEM_PROPERTY, "http://somehost:80");

    Config config = new Config();
    assertNotNull(config);
    assertEquals("http://somehost:80/", config.getMasterUrl());
    assertEquals("testns", config.getNamespace());
    assertEquals("token", config.getOauthToken());
  }

  @Test
  public void testWithKubeConfigAndSytemPropertiesAndBuilder() {
    System.setProperty(Config.KUBERNETES_KUBECONFIG_FILE, getClass().getResource("/test-kubeconfig").getFile());
    System.setProperty(Config.KUBERNETES_MASTER_SYSTEM_PROPERTY, "http://somehost:80");

    Config config = new ConfigBuilder()
      .withNamespace("testns2")
      .build();

    assertNotNull(config);
    assertEquals("http://somehost:80/", config.getMasterUrl());
    assertEquals("token", config.getOauthToken());
    assertEquals("testns2", config.getNamespace());
  }

  private void assertConfig(Config config) {
    assertNotNull(config);
    assertTrue(config.isTrustCerts());
    assertEquals("http://somehost:80/", config.getMasterUrl());
    assertEquals("testns", config.getNamespace());
    assertEquals("token", config.getOauthToken());
    assertEquals("user", config.getUsername());
    assertEquals("pass", config.getPassword());
    assertEquals("/path/to/cert", config.getCaCertFile());
    assertEquals("cacertdata", config.getCaCertData());
    assertEquals("/path/to/clientcert", config.getClientCertFile());
    assertEquals("clientcertdata", config.getClientCertData());

    assertEquals("/path/to/clientkey", config.getClientKeyFile());
    assertEquals("clientkeydata", config.getClientKeyData());
    assertEquals("algo", config.getClientKeyAlgo());
    assertEquals("passphrase", config.getClientKeyPassphrase());

    assertEquals("httpProxy", config.getProxy());

    assertEquals(5000, config.getWatchReconnectInterval());
    assertEquals(5, config.getWatchReconnectLimit());
    assertEquals(5000, config.getRequestTimeout());
  }
}
