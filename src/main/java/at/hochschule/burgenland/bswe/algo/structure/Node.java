/**
 * ----------------------------------------------------------------------------- File: Node.java
 * Package: at.hochschule.burgenland.bswe.algo.structure Authors: Alexander R. Brenner, Raja
 * Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.structure;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Graph node representing one airport and its outgoing connections. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {

  /** IATA code of this airport. */
  private String iata;

  /** List of outgoing edges (flights) from this airport. */
  private List<Edge> outgoingEdges = new ArrayList<>();
}
