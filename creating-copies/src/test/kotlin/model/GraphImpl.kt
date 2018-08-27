package model

import graphParts.Graph
import graphParts.Vertex

class GraphImpl(override val vertices: List<Vertex<*, *>>) : Graph