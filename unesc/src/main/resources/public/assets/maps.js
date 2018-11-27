

(function () {
  //Constantes
  const TEXT_SIZE = 12;
  const TEXT_COLOR = 'black';
  const ALTERNATIVE_COLOR = 'blue';
  const LINE_COLOR = 'red';
  const LINE_COLOR_HOVER = '#ff4f4f';
  const IMAGE_MAP_SRC = './assets/mapa.png';

  fabric.Object.prototype.originX = fabric.Object.prototype.originY = 'center';

  function makeIntermediaryCircle(x, y, alternativeColor) {
    var c = new fabric.Circle({
      left: x - 2,
      top: y - 2,
      strokeWidth: 0,
      radius: 4,
      fill: alternativeColor ? ALTERNATIVE_COLOR : LINE_COLOR,
      selectable: false,
      evented: false,
    });
    c.hasControls = c.hasBorders = false;

    return c;
  }

  function makePlaceCircle(x, y) {
    var c = new fabric.Circle({
      left: x - 4,
      top: y - 4,
      strokeWidth: 2,
      radius: 6,
      fill: '#fff',
      stroke: LINE_COLOR,
      selectable: false,
      evented: false,
    });
    c.hasControls = c.hasBorders = false;

    return c;
  }

  function makeLinePath(x1, y1, x2, y2, description) {
    var line = new fabric.Line([x1, y1, x2, y2], {
      fill: LINE_COLOR,
      stroke: LINE_COLOR,
      strokeWidth: 4,
      selectable: false,
      //evented: false,
    });

    line.description = description;

    return line;
  }

  function makeDescription(x1, y1, x2, y2, description) {
    var middleX = (x1 + x2) / 2 + 15;
    var middleY = (y1 + y2) / 2 - 3;

    return new fabric.Text(description, {
      fontSize: 12,
      left: middleX,
      top: middleY,
      lineHeight: 1,
      originX: 'left',
      fontFamily: 'Helvetica',
      textBackgroundColor: 'rgba(255, 255, 255, 1)',
      statefullCache: true,
      scaleX: 1,
      scaleY: 1,
      visible: false,
      selectable: false,
      evented: false,
    });
  }

  function makeNodeName(x, y, name, alternativeColor) {
    return new fabric.Text(" " + name + " ", {
      fontSize: 12,
      left: x + 18,
      top: y - 6,
      lineHeight: 1,
      originX: 'left',
      fontFamily: 'Helvetica',
      fontWeight: 'bold',
      fill: alternativeColor ? ALTERNATIVE_COLOR : TEXT_COLOR,
      textBackgroundColor: alternativeColor ? 'rgba(255, 255, 255, 0.5)' : 'rgba(255, 255, 255, 0.85)',
      statefullCache: true,
      scaleX: 1,
      scaleY: 1,
      selectable: false,
      evented: false,
    });
  }

  async function generateMap(canvas, paths, nodes) {
    return new Promise((resolve) => {
      const response = {};

      fabric.Image.fromURL(IMAGE_MAP_SRC, (mapImage) => {
        response.elements = [];

        paths.forEach((path) => {
          let lastExcerpt;
          path.excerpts.forEach((excerpt, index) => {
            let descriptionObj;

            //Cria a linha
            if (lastExcerpt) {
              descriptionObj = excerpt.description ? makeDescription(lastExcerpt.x, lastExcerpt.y, excerpt.x, excerpt.y, excerpt.description) : {};
            }

            //Cria ponto inicial
            if (index === 1) {
              //Só imprime nome do ponto for "place" ou não for "place"
              if (lastExcerpt && lastExcerpt.place) {
                response.elements.push(makeNodeName(lastExcerpt.x, lastExcerpt.y, lastExcerpt.name));
              }

              //Só imprime ponto destacado se for "place"
              if (lastExcerpt && lastExcerpt.place) {
                response.elements.unshift(makePlaceCircle(lastExcerpt.x, lastExcerpt.y));
              } else {
                response.elements.unshift(makeIntermediaryCircle(lastExcerpt.x, lastExcerpt.y));
              }
            }

            //Cria ponto final
            if (index === (path.excerpts.length - 1)) {
              if (excerpt && excerpt.place) {
                response.elements.push(makeNodeName(excerpt.x, excerpt.y, excerpt.name));
              }

              //Só imprime ponto destacado se for "place"
              if (excerpt.place) {
                response.elements.unshift(makePlaceCircle(excerpt.x, excerpt.y));
              } else {
                response.elements.unshift(makeIntermediaryCircle(excerpt.x, excerpt.y));
              }

              //Cria pontos intermediários
            } else if (index >= 1) {
              //Descomente para ativar ponto intermediário
              response.elements.push(makeIntermediaryCircle(excerpt.x, excerpt.y));
            }

            //Adiciona descrição para os elementos
            if (descriptionObj && descriptionObj instanceof fabric.Text) {
              response.elements.push(descriptionObj);
            }

            //Cria a linha
            if (lastExcerpt) {
              response.elements.unshift(makeLinePath(lastExcerpt.x, lastExcerpt.y, excerpt.x, excerpt.y, descriptionObj));
            }

            //Armazena o último trecho afim de criar a linha na próxima iteração
            lastExcerpt = excerpt;
          });
        });

        //Insere a imagem no início do array para garantir que a mesma seja o primeiro nível
        response.elements.unshift(mapImage);

        //Se for passado nós, imprime na tela
        if (nodes) {
          for (i in nodes) {
            let node = nodes[i];
            response.elements.push(makeNodeName(node.x, node.y, node.name, !node.place));
            if (node.place) {
              response.elements.push(makePlaceCircle(node.x, node.y));
            } else {
              response.elements.push(makeIntermediaryCircle(node.x, node.y, true));
            }
          }
        }

        //Sanitize elementos para eliminar locais duplicados
        const sanitizedElements = response.elements.filter((element, index, self) => {
          return (!element.text || self.findIndex((t) => t.left === element.left && t.top === element.top) === index);
        });

        //Grupo de elementos que compoe o mapa
        response.map = new fabric.Group(sanitizedElements, {
          left: 0,
          top: 0,
          subTargetCheck: true,
          objectCaching: false,
          withoutTransform: true
        });

        //Add map to canvas
        canvas.add(response.map);
        canvas.item(0).hasControls = false;
        canvas.item(0).hasBorders = false;

        //Mouse hover
        response.map.on('mouseover', () => {
          canvas.on('mouse:move', onMouseMove)
        });
        response.map.on('mouseout', () => {
          onMouseMove();
          canvas.off('mouse:move', onMouseMove)
        });

        let hoveredItem;
        const onMouseMove = (option) => {
          if (option && option.subTargets[0] && (option.subTargets[0].type == 'line')) {
            if (hoveredItem && hoveredItem !== option.subTargets[0]) {
              hoveredItem.set('stroke', LINE_COLOR);
              hoveredItem.description.visible = false;
            }

            option.subTargets[0].set('stroke', LINE_COLOR_HOVER);
            option.subTargets[0].description.visible = true;

            if (option.subTargets[0].description && typeof option.subTargets[0].description.bringToFront === 'function') {
              option.subTargets[0].description.bringToFront();
            }

            hoveredItem = option.subTargets[0];
          } else {
            if (hoveredItem) {
              hoveredItem.set('stroke', LINE_COLOR);
              hoveredItem.description.visible = false;
            }
          }

          response.map.dirty = true;
          canvas.requestRenderAll();
        }

        resolve(response);
      });
    });
  }

  function scaleMap(map, scale) {
    if (!map instanceof fabric.Group) {
      throw 'Invalid map object'
    }

    if (scale > 1 || scale < 0.5) {
      throw 'Invalid scale parameter';
    }

    //Diminui o grupo que compoe o mapa
    map.scale(scale);

    //Aumenta as descrições e nomes de pontos
    map.getObjects().forEach((object) => {
      if (object && object.get('type') === 'text') {
        object.fontSize = TEXT_SIZE * (1 + (1 - scale));
      }
    });
  }

  function moveMap(map, left, top) {
    if (!map instanceof fabric.Group) {
      throw 'Invalid map object'
    }

    map.set('left', left);
    map.set('top', top);
    map.setCoords();
  }

  function getPathByNodes(paths, originNode, destinationNode, reducedMobility) {
    return paths.filter((path) => {
      const originExists = path.originNode.name === originNode.name;
      const destinationExists = !destinationNode || (path.destinationNode.name === destinationNode.name);
      const reducedMobilityExists = reducedMobility === undefined || path.reducedMobility === reducedMobility;

      return originExists && destinationExists && reducedMobilityExists;
    });
  }

  //IMPLEMENTAÇÃO
  var canvas = this.__canvas = new fabric.Canvas('c', {
    selection: false,
    backgroundColor: "#fff",

    enableRetinaScaling: true,
    //imageSmoothingEnabled: false,
  });
  fabric.Object.prototype.originX = 'left';
  fabric.Object.prototype.originY = 'top';

  //Cria o mapa
  generateMap(canvas, [])
    .then((response) => {
      //Escala mapa
      const scale = 1;

      scaleMap(response.map, scale);

      //Centraliza o caminho inicial no centro do mapa
      const initialPositionX = 650;
      const initialPositionY = 1840;
      const left = -((initialPositionX * scale) - (canvas.getWidth() / 2));
      const top = -((initialPositionY * scale) - (canvas.getHeight() / 2));

      moveMap(response.map, left, top);
    });
})();