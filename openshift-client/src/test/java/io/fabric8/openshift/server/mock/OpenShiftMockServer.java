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

package io.fabric8.openshift.server.mock;

import io.fabric8.kubernetes.api.model.RootPathsBuilder;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.server.mock.KubernetesMockServer;
import io.fabric8.openshift.client.DefaultOpenshiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

import java.io.IOException;

public class OpenShiftMockServer extends KubernetesMockServer {

  @Override
  public void init() throws IOException {
    super.init();
    expectAndReturnAsJson("/", 200, new RootPathsBuilder().addToPaths("/api", "/oapi").build());
  }

  public OpenShiftClient createOpenShiftClient() {
    Config config = new ConfigBuilder()
      .withMasterUrl("http://localhost:" + getServer().getPort())
      .withNamespace("test")
      .build();
    return new DefaultOpenshiftClient(config);
  }
}
