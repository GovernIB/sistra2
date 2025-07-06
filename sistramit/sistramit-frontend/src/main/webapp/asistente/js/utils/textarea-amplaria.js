// TEXTAREA -> AMPLARIA

function limitTextareaChars(textarea, maxLength = 4000, counter = null) {
    
    /**
     * Calcula la longitud real del texto considerando saltos de línea
     * Chrome usa \n pero Oracle espera \r\n, por lo que cada \n cuenta como 2 caracteres
     */
    function getRealLength(text) {
        // Contar saltos de línea simples (\n que no están precedidos por \r)
        const singleLineBreaks = (text.match(/(?<!\r)\n/g) || []).length;
        // La longitud real es la longitud del texto + saltos de línea simples
        return text.length + singleLineBreaks;
    }
    
    /**
     * Trunca el texto manteniendo la integridad de los saltos de línea
     */
    function truncateText(text, maxLen) {
        if (getRealLength(text) <= maxLen) {
            return text;
        }
        
        let truncated = '';
        let realLength = 0;
        
        for (let i = 0; i < text.length; i++) {
            const char = text[i];
            const isLineBreak = char === '\n' && (i === 0 || text[i-1] !== '\r');
            const charWeight = isLineBreak ? 2 : 1;
            
            if (realLength + charWeight > maxLen) {
                break;
            }
            
            truncated += char;
            realLength += charWeight;
        }
        
        return truncated;
    }
    
    /**
     * Actualiza el contador si se proporciona
     */
    function updateCounter(current, max) {
        if (counter) {
            counter.textContent = `${current}/${max}`;
            counter.className = current > max * 0.9 ? 'text-warning' : '';
            if (current === max) {
                counter.className = 'text-danger';
            }
        }
    }
    
    /**
     * Maneja el evento de entrada de texto
     */
    function handleInput(event) {
        const currentText = event.target.value;
        const realLength = getRealLength(currentText);
        
        if (realLength > maxLength) {
            // Truncar el texto y actualizar el textarea
            const truncatedText = truncateText(currentText, maxLength);
            event.target.value = truncatedText;
            
            // Mantener la posición del cursor lo más cerca posible
            const cursorPos = Math.min(event.target.selectionStart, truncatedText.length);
            event.target.setSelectionRange(cursorPos, cursorPos);
        }
        
        updateCounter(getRealLength(event.target.value), maxLength);
    }
    
    /**
     * Maneja el evento de pegado
     */
    function handlePaste(event) {
        event.preventDefault();
        
        const pastedText = (event.clipboardData || window.clipboardData).getData('text');
        const currentText = event.target.value;
        const selectionStart = event.target.selectionStart;
        const selectionEnd = event.target.selectionEnd;
        
        // Construir el nuevo texto
        const newText = currentText.substring(0, selectionStart) + 
                       pastedText + 
                       currentText.substring(selectionEnd);
        
        // Aplicar la limitación
        const finalText = truncateText(newText, maxLength);
        event.target.value = finalText;
        
        // Posicionar el cursor después del texto pegado (o truncado)
        const newCursorPos = Math.min(
            selectionStart + pastedText.length,
            finalText.length
        );
        event.target.setSelectionRange(newCursorPos, newCursorPos);
        
        updateCounter(getRealLength(finalText), maxLength);
        
        // Disparar evento input para otros listeners
        event.target.dispatchEvent(new Event('input', { bubbles: true }));
    }
    
    // Configurar los event listeners
    textarea.addEventListener('input', handleInput);
    textarea.addEventListener('paste', handlePaste);
    
    // Configuración inicial
    updateCounter(getRealLength(textarea.value), maxLength);
    
    // Retornar objeto con métodos útiles
    return {
        getCurrentLength: () => getRealLength(textarea.value),
        getMaxLength: () => maxLength,
        getRemainingChars: () => maxLength - getRealLength(textarea.value),
        destroy: () => {
            textarea.removeEventListener('input', handleInput);
            textarea.removeEventListener('paste', handlePaste);
        }
    };
}