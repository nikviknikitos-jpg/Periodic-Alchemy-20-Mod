package com.example.chemtable.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ChemBookScreen extends Screen {
    private int page = 0;
    private static final int TOTAL = 5;
    private static final String[][] RECIPES = {
        {"Чистая Вода: H + H + O", "Гремучий Газ: H + O", "Щелочной Взрыв: Na + H + O", "Калиевый Детонатор: K + Na + H + O"},
        {"Белый Фосфор: P + O", "Греческий Огонь: P + S + O + C", "Хлорное Облако: Cl + Cl / K + Cl", "Ипритовый Туман: C + H + Cl + S"},
        {"Жидкий Азот: N + H", "Абсолютный Ноль: N + O + Cl", "Кислота: H + Cl + N + O", "Кварцевая Пена: Ca + C + O"},
        {"Обсидиановый Гель: Ca + Fe + C + O", "ЭМИ: Fe + K + Cl", "Термоядерный Синтез: U + H + O + N", "Сыворотка Титана: Fe + Ca + C"},
        {"Адреналиновый Коктейль: N + O + H", "Люминофорный Экстракт: P + K + O", "Термостойкий Полимер: S + Fe + O", "Философский Катализатор: U + Fe + C + P"},
    };
    private static final String[] NOTES = {
        "Собери до 4 элементов в Реакторе (ПКМ элементами).",
        "ПКМ Стабилизатором — запечатать в колбу-гранату.",
        "Shift+ПКМ пустой рукой по Реактору — запустить.",
        "Без стабилизатора реакция идет 3 секунды прямо вокруг реактора!",
        "ПКМ гранатой или эликсиром — метнуть или выпить."
    };

    protected ChemBookScreen() { super(Text.literal("Справочник Химика")); }

    @Override
    protected void init() {
        super.init();
        int left = (this.width - 250) / 2;
        int top = (this.height - 185) / 2;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("< Назад"), b -> {
            if (page > 0) page--;
        }).dimensions(left + 10, top + 190, 80, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Вперед >"), b -> {
            if (page < TOTAL - 1) page++;
        }).dimensions(left + 160, top + 190, 80, 20).build());
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx);
        int w = 250, h = 185;
        int left = (this.width - w) / 2;
        int top = (this.height - h) / 2;
        ctx.fill(left, top, left + w, top + h, 0xFFF4E2C0);
        ctx.fill(left, top, left + w, top + 6, 0xFF6A4A28);
        ctx.fill(left, top + h - 6, left + w, top + h, 0xFF6A4A28);
        ctx.fill(left, top, left + 6, top + h, 0xFF6A4A28);
        ctx.fill(left + w - 6, top, left + w, top + h, 0xFF6A4A28);

        ctx.drawText(this.textRenderer, Text.literal("Справочник Химика — Стр. " + (page + 1) + "/" + TOTAL),
                left + 12, top + 12, 0x442200, false);
        ctx.drawText(this.textRenderer, Text.literal(NOTES[page]), left + 12, top + 30, 0xAA3300, false);
        int y = top + 50;
        for (String line : RECIPES[page]) {
            ctx.drawText(this.textRenderer, Text.literal(line), left + 14, y, 0x442200, false);
            y += 22;
        }
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }
}
