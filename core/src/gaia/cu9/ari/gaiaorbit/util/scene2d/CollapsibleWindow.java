/*
 * This file is part of Gaia Sky, which is released under the Mozilla Public License 2.0.
 * See the file LICENSE.md in the project root for full license details.
 */

/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package gaia.cu9.ari.gaiaorbit.util.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import gaia.cu9.ari.gaiaorbit.util.GlobalConf;
import gaia.cu9.ari.gaiaorbit.util.GlobalResources;
import gaia.cu9.ari.gaiaorbit.util.I18n;

/**
 * A {@code CollapsableWindow} can be expanded/collapsed with a single click on
 * the title bar.
 *
 * @author Xoppa
 * @author Toni Sagrista
 **/
public class CollapsibleWindow extends Window {
    private boolean collapsed, collapsing = false, expanding = false;
    private float collapseHeight;
    private float expandHeight;
    private Vector2 vec2;
    protected Actor me;
    protected Skin skin;

    private float maxWidth = -1, maxHeight = -1;
    /**
     * Collapse speed in pixels per second
     **/
    protected float collapseSpeed;

    String expandIcon = "window-expand";
    String collapseIcon = "window-collapse";

    public CollapsibleWindow(String title, Skin skin) {
        this(title, skin, 2000);
    }

    public CollapsibleWindow(String title, Skin skin, float collapseSpeed) {
        super(title, skin);
        this.me = this;
        this.skin = skin;
        this.collapseSpeed = collapseSpeed;
        this.collapseHeight = 20f * GlobalConf.SCALE_FACTOR;

        vec2 = new Vector2();
        addListener(new ClickListener() {
            private float startx, starty;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                startx = x + getX();
                starty = y + getY();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                float endx = x + getX();
                float endy = y + getY();
                vec2.set(endx - startx, endy - starty);
                // pixels of margin
                if (vec2.len() < 3) {
                    if (getHeight() - y <= getPadTop() && y < getHeight() && x > 0 && x < getWidth())
                        if (button == Input.Buttons.LEFT) {
                            // Left mouse button on title toggles collapse
                            toggleCollapsed();
                        }
                }
                super.touchUp(event, x, y, pointer, button);
            }

        });

        // Pad title cell
        getTitleTable().getCells().get(0).padLeft(5 * GlobalConf.SCALE_FACTOR);
        // Mouse pointer on title
        getTitleTable().addListener(event -> {
            if (event instanceof InputEvent) {
                Type type = ((InputEvent) event).getType();
                // Click
                if (type == Type.mouseMoved) {
                    Gdx.graphics.setCursor(GlobalResources.linkCursor);
                } else if (type == Type.exit) {
                    Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
                }
                return true;
            }
            return false;
        });

        addListener(event -> {
            if (isResizable()) {
                if (event instanceof InputEvent) {
                    Type type = ((InputEvent) event).getType();
                    if (type == Type.mouseMoved) {
                        if ((edge & Align.bottom) != 0 && maxHeight == -1) {
                            Gdx.graphics.setCursor(GlobalResources.resizeYCursor);
                        } else if ((edge & Align.right) != 0 && maxWidth == -1) {
                            Gdx.graphics.setCursor(GlobalResources.resizeXCursor);
                        }
                    } else if (type == Type.exit) {
                        Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
                    }

                }
            }
            return false;
        });
        getTitleTable().addListener(new TextTooltip(I18n.bundle.get("gui.tooltip.expandcollapse"), skin));

    }

    protected void drawBackground(Batch batch, float parentAlpha, float x, float y) {
        float width = getWidth(), height = getHeight();
        float padTop = getPadTop();

        super.drawBackground(batch, parentAlpha, x, y);

        Drawable icon = collapsed ? skin.getDrawable(expandIcon) : skin.getDrawable(collapseIcon);
        float iw = icon.getMinWidth();
        float ih = icon.getMinHeight();

        x += width - iw - getPadRight();
        y += height - getPadTop() / 2;
        y -= (padTop - ih) / 2;

        icon.draw(batch, x, y, iw, ih);

    }

    public void act(float delta) {
        super.act(delta);

        if (collapsing) {
            float pixels = collapseSpeed * delta;
            // COLLAPSING
            if (getHeight() > collapseHeight) {
                float currHeight = getHeight();
                float newHeight = Math.max(collapseHeight, currHeight - pixels);
                setHeight(newHeight);
                setY(getY() + (currHeight - newHeight));
            } else {
                if (getStage() != null)
                    getStage().setScrollFocus(null);
                collapsing = false;
                collapsed = true;
            }
        } else if (expanding) {
            float pixels = collapseSpeed * delta;
            // EXPANDING
            if (getHeight() < expandHeight) {
                float currHeight = getHeight();
                float newHeight = Math.min(expandHeight, currHeight + pixels);
                setHeight(newHeight);
                setY(getY() + (currHeight - newHeight));
            } else {
                expanding = false;
                collapsed = false;
            }
        }
    }

    public void expand() {
        if (!collapsed || expanding || collapsing)
            return;
        else {
            expanding = true;
        }

    }

    public void expandInstant() {
        if (!collapsed)
            return;
        setHeight(expandHeight);
        setY(getY() - expandHeight + collapseHeight);
        collapsed = false;
    }

    public void collapse() {
        if (collapsed || expanding || collapsing)
            return;
        else {
            expandHeight = getHeight();
            collapsing = true;
        }
    }

    public void collapseInstant() {
        if (collapsed)
            return;
        expandHeight = getHeight();
        setHeight(collapseHeight);
        setY(getY() + expandHeight - collapseHeight);
        collapsed = true;
        if (getStage() != null)
            getStage().setScrollFocus(null);
    }

    public void toggleCollapsed() {
        if (collapsed)
            expand();
        else
            collapse();
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    @Override
    public void pack() {
        collapsed = false;
        super.pack();
    }

    public void setResizable(boolean w, boolean h) {
        setResizable(w || h);
        if (w) {
            maxWidth = -1;
        } else {
            pack();
            maxWidth = this.getWidth();
        }
        if (h) {
            maxHeight = -1;
        } else {
            pack();
            maxHeight = this.getHeight();
        }
    }

    @Override
    public float getPrefWidth() {
        if (maxWidth < 0)
            return super.getPrefWidth();
        else
            return maxWidth;
    }

    @Override
    public float getMaxWidth() {
        if (maxWidth < 0)
            return super.getMaxWidth();
        else
            return maxWidth;
    }

    @Override
    public float getWidth() {
        if (maxWidth < 0)
            return super.getWidth();
        else
            return maxWidth;
    }

    @Override
    public float getPrefHeight() {
        if (maxHeight < 0)
            return super.getPrefHeight();
        else
            return maxHeight;
    }

    @Override
    public float getMaxHeight() {
        if (maxHeight < 0)
            return super.getMaxHeight();
        else
            return maxHeight;
    }

    @Override
    public float getHeight() {
        if (maxHeight < 0)
            return super.getHeight();
        else
            return maxHeight;
    }
}