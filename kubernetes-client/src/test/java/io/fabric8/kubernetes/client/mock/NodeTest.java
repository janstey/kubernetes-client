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

package io.fabric8.kubernetes.client.mock;

import io.fabric8.kubernetes.api.model.Node;
import io.fabric8.kubernetes.api.model.NodeList;
import io.fabric8.kubernetes.api.model.NodeListBuilder;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class NodeTest extends KubernetesMockServerTestBase {

  @Test
  public void testList() {
    expectAndReturnAsJson("/api/v1/nodes", 200, new NodeListBuilder()
      .addNewItem().and().build());

    KubernetesClient client = getClient();
    NodeList nodeList = client.nodes().list();
    assertNotNull(nodeList);
    assertEquals(1, nodeList.getItems().size());
  }


  @Test
  public void testGet() {
    expectAndReturnAsJson("/api/v1/nodes/node1", 200, new PodBuilder().build());
    expectAndReturnAsJson("/api/v1/nodes/node2", 200, new PodBuilder().build());

    KubernetesClient client = getClient();

    Node node = client.nodes().withName("node1").get();
    assertNotNull(node);

    node = client.nodes().withName("node2").get();
    assertNotNull(node);

    node = client.nodes().withName("node3").get();
    assertNull(node);
  }


  @Test
  public void testDelete() {
    expectAndReturnAsJson("/api/v1/nodes/node1", 200, new PodBuilder().build());
    expectAndReturnAsJson("/api/v1/nodes/node2", 200, new PodBuilder().build());

    KubernetesClient client = getClient();

    Boolean deleted = client.nodes().withName("node1").delete();
    assertTrue(deleted);

    deleted = client.nodes().withName("node2").delete();
    assertTrue(deleted);


    deleted = client.nodes().withName("node3").delete();
    assertFalse(deleted);
  }
}
