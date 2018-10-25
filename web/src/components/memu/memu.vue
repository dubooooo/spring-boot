<template>
  <div class="memu">
    <h1>{{msg}}</h1>
    <ul>
      <li v-for="book in books">{{book.name}}</li>
    </ul>
  </div>

</template>

<script>
  export default {
    name: "Memu",
    data() {
      return {
        msg: "hello memu",
        books: [
          {name: '<<book01>>'},
          {name: '<<book02>>'},
          {name: '<<book03>>'},
          {name: '<<book04>>'}
        ]
      }
    }
  }
</script>

<style scoped>

</style>
