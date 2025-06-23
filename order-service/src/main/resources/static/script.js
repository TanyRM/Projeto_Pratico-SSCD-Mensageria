let carrinho = [];

window.onload = () => {
    fetch('/products') // ajusta se estiver em outra porta
        .then(res => res.json())
        .then(produtos => {
            const div = document.getElementById('produtos');
            produtos.forEach(p => {
                const el = document.createElement('div');
                el.innerHTML = `
          <p><strong>${p.nome}</strong></p>
          <img src="${p.imagem_url}" alt="${p.nome}" />
          <p>Preço: R$ ${(p.preco / 100).toFixed(2)}</p>
          <p>Estoque: ${p.estoque}</p>
          <input type="number" id="qtd-${p.id}" min="1" max="${p.estoque}" value="1" />
          <button onclick="addCarrinho('${p.id}', '${p.nome}', ${p.preco})">Adicionar</button>
        `;
                div.appendChild(el);
            });
        });
};

function addCarrinho(id, nome, preco) {
    const qtd = parseInt(document.getElementById(`qtd-${id}`).value);
    const existente = carrinho.find(item => item.produtoId === id);
    if (existente) {
        existente.quantidade += qtd;
    } else {
        carrinho.push({ produtoId: id, nome, quantidade: qtd, precoUnidade: preco });
    }
    renderCarrinho();
}

function renderCarrinho() {
    const div = document.getElementById('carrinho');
    div.innerHTML = '';
    let total = 0;
    carrinho.forEach(item => {
        const subtotal = item.quantidade * item.precoUnidade;
        total += subtotal;
        div.innerHTML += `<div>
      ${item.nome} - ${item.quantidade}x - R$ ${(subtotal / 100).toFixed(2)}
    </div>`;
    });
    document.getElementById('total').innerText = `Total: R$ ${(total / 100).toFixed(2)}`;
}

function mostrarFormulario() {
    document.getElementById('formulario').style.display = 'block';
}

function enviarPedido() {
    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const telefone = document.getElementById('telefone').value;

    const pedido = {
        customer: { nome, email, telefone },
        items: carrinho.map(item => ({
            produtoId: item.produtoId,
            quantidade: item.quantidade,
            precoUnidade: item.precoUnidade
        }))
    };

    fetch('/orders', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(pedido)
    })
        .then(res => res.json())
        .then(data => {
            consultarStatus(data.orderId);
        });
}

function consultarStatus(orderId) {
    setTimeout(() => {
        fetch(`/orders/${orderId}`)
            .then(res => res.json())
            .then(data => {
                const statusDiv = document.getElementById('statusPedido');
                statusDiv.innerHTML = `<h3>Status do Pedido</h3><p>${data.status}</p>`;
            });
    }, 3000); // espera alguns segundos para o backend atualizar o status
}
