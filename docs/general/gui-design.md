Principles behind the GUI design
==========================================

## CSS and JS framework
nikita uses mdl (material design lite) as the underlying GUI CSS framework. 
Principles behind and more information about mdl can be found 
[here](https://getmdl.io/index.html).   

For JS, we are using angular. We originally used angular 1.6 but are in the
process of upgrading to angular 4 (or whatever number it has). The reason for 
this is that there are som bugs in angular 1.6 with regards to mdl, which
has us adding some dirty hacks to get around the bugs. This is also chosen to
maximise the available developer resources.

Currently nikita-gui has not been developed as a single page application, but 
we hope to change that later. 

The GUI has four distinct roles it has to support:

  - casehandler
  - leader
  - recordskeeper
  - adminstrator

The casehandandler and leader could probably be built as a single page. The
recordskeeper and administrator roles require their own pages.

