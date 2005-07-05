/*
 * ====================================================================
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * Copyright (C) 2003-2004 TONBELLER AG.
 * All Rights Reserved.
 * You must accept the terms of that agreement to use this software.
 * ====================================================================
 *
 * 
 */
package com.tonbeller.wcf.tree;

import com.tonbeller.wcf.component.RenderListener;
import com.tonbeller.wcf.controller.RequestListener;

/**
 * A NodeRenderer that is a RequestListener too. The tree will install
 * this NodeRenderer as a default listener (i.e. all Requests are forwareded to this).
 * <p>
 * usage pattern:
 * <pre>
 * MyRequestListeningNodeRenderer extends DispatcherSupport implements RequestListeningNodeRenderer {
 *   void startRendering() {
 *     // clear all RequestListeners
 *     super.clear();
 *   }
 *   Element renderNode(..) {
 *     // create and add RequestListeners that responds to buttons at the node
 *   }
 *   void stopRendering() {
 *     // any cleanup here
 *   }
 *   
 * @author av
 */
public interface RequestListeningNodeRenderer extends NodeRenderer, RequestListener, RenderListener {
  
}
