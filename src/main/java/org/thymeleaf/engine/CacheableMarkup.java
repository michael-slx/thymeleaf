/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.engine;

import java.io.StringWriter;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.model.IAutoCloseElementTag;
import org.thymeleaf.model.IAutoOpenElementTag;
import org.thymeleaf.model.ICDATASection;
import org.thymeleaf.model.ICloseElementTag;
import org.thymeleaf.model.IComment;
import org.thymeleaf.model.IDocType;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.model.IProcessingInstruction;
import org.thymeleaf.model.IStandaloneElementTag;
import org.thymeleaf.model.IText;
import org.thymeleaf.model.IUnmatchedCloseElementTag;
import org.thymeleaf.model.IXMLDeclaration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.TemplateResolution;
import org.thymeleaf.util.Validate;


/**
 *
 * @author Daniel Fern&aacute;ndez
 * @since 3.0.0
 *
 */
public final class CacheableMarkup implements IMarkup {

    private final Markup markup;



    // Package-protected constructor, because we don't want anyone creating these objects from outside the engine.
    // Specifically, there will only be created from the TemplateProcessor.
    // If a processor (be it standard or custom-made) wants to create a piece of markup, that should be a Markup
    // object, not this.
    CacheableMarkup(final IEngineConfiguration configuration, final TemplateResolution templateResolution) {
        super();
        // Validity CAN be null
        this.markup = new Markup(configuration, templateResolution.getTemplateMode());
    }


    // Meant to be called only from the cloneMarkup method
    private CacheableMarkup(final Markup markup) {
        super();
        this.markup = markup;
    }


    public final IEngineConfiguration getConfiguration() {
        return this.markup.getConfiguration();
    }


    public final TemplateMode getTemplateMode() {
        return this.markup.getTemplateMode();
    }


    // We don't want anyone to have direct access to the underlying Markup object from outside the engine.
    // This will effectively turn our ParsedFragmentMarkup into immutable (though not really) and therefore allow us
    // to confidently cache these objects without worrying that anyone can modify them
    final Markup getMarkup() {
        return this.markup;
    }



    public final String computeMarkup() {
        return this.markup.computeMarkup();
    }


    public IMarkup cloneMarkup() {
        return new CacheableMarkup((Markup)this.markup.cloneMarkup());
    }



    @Override
    public String toString() {
        return computeMarkup();
    }



}